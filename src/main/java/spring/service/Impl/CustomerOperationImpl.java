package spring.service.Impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ChangeCustomerDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterCustomerDto;
import spring.dto.ResponceCustomerDto;
import spring.entity.Credit;
import spring.entity.Customer;
import spring.enumaration.Role;
import spring.mapper.Mapper;
import spring.repository.CustomerGateway;
import spring.service.CustomerOperation;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomerOperationImpl implements CustomerOperation {
    private final CustomerGateway customerGateway;

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());

    }

    @Override
    @Transactional
    public void register(RegisterCustomerDto customerDto) {

        checkInputIsExistsInDB(customerDto);
        Customer customer = Mapper.ConvertDtoToEntity.convertCustomerDtoToEntity(customerDto);
        customer.setRole(Role.Customer);
        customer.setCredit(Credit.builder().build());
        customer.setPassword(hashPassword(customerDto.password()));
        customerGateway.save(customer);

    }

    private void checkInputIsExistsInDB(RegisterCustomerDto customerDto) {

        Set<String> customers = CheckInputFromDBOperation.checkUserInfoFromDB("Customer",
                customerGateway.existUserByNationalCode(customerDto.nationalCode()),
                customerGateway.existUserByMobileNumber(customerDto.mobileNumber()),
                customerGateway.existUserByEmailAddress(customerDto.emailAddress()),
                customerGateway.existUserByUserName(customerDto.userName()));
        if (!customers.isEmpty())
            throw new EntityExistsException(String.join(", ", customers));

    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordDto passwordDto) {

        Customer customer = customerGateway.findById(passwordDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));
       // if (customer.getPassword().equals(passwordDto.oldPassword())) {
        if (BCrypt.checkpw(passwordDto.oldPassword(), customer.getPassword())) {
            customer.setPassword(hashPassword(passwordDto.password()));
            customerGateway.save(customer);
        } else
            throw new IllegalArgumentException("Old password does not match");
    }

    @Override
    @Transactional
    public void update(ChangeCustomerDto customerDto) {
        validateCustomerDataForUpdate(customerDto);
        Customer customer = findById(customerDto.customerId());
        updateOperation(customerDto, customer);
    }

    private void validateCustomerDataForUpdate(ChangeCustomerDto customerDto) {
        Set<String> errors = new HashSet<>();

        Map<String, Boolean> validationMap = new HashMap<>();
        validationMap.put("Mobile Number", customerDto.mobileNumber() != null
                                           && customerGateway.existUserByMobileNumber(customerDto.mobileNumber()));
        validationMap.put("National Code", customerDto.nationalCode() != null
                                           && customerGateway.existUserByNationalCode(customerDto.nationalCode()));
        validationMap.put("Email Address", customerDto.emailAddress() != null
                                           && customerGateway.existUserByEmailAddress(customerDto.emailAddress()));
        validationMap.put("Username", customerDto.userName() != null
                                      && customerGateway.existUserByUserName(customerDto.userName()));

        validationMap.forEach((fieldName, exists) -> {
            if (exists) {
                errors.add("Customer with this " + fieldName + " already exists");
            }
        });

        if (!errors.isEmpty()) {
            throw new EntityExistsException(String.join(", ", errors));
        }
    }

    private void updateOperation(ChangeCustomerDto customerDto, Customer customer) {

        applyIfNotNull(customerDto.firstName(), customer::setFirstName);
        applyIfNotNull(customerDto.lastName(), customer::setLastName);
        applyIfNotNull(customerDto.mobileNumber(), customer::setMobileNumber);
        applyIfNotNull(customerDto.nationalCode(), customer::setNationalCode);
        applyIfNotNull(customerDto.emailAddress(), customer::setEmailAddress);
        applyIfNotNull(customerDto.userName(), customer::setUserName);
        customerGateway.save(customer);
    }

    private <T> void applyIfNotNull(T value, Consumer<T> setterMethod) {
        if (value != null) {
            setterMethod.accept(value);
        }
    }

    @Override
    public Customer findById(Long customerId) {
        if (customerId == null)
            throw new IllegalArgumentException("customerId can not be Null");
        return customerGateway.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));

    }

    @Override
    public List<ResponceCustomerDto> findAllCustomers() {
        List<Customer> customers = customerGateway.findAll();
        List<ResponceCustomerDto> responceSubServiceDtos = customers.stream()
                .map(Mapper.ConvertEntityToDto::convertCustomerToDto).toList();
        //.map(customer -> Mapper.ConvertEntityToDto.convertCustomerToDto(customer)).toList();
        if (responceSubServiceDtos.isEmpty())
            throw new NoSuchElementException("There are currently no customers.");
        return responceSubServiceDtos;
    }
}
/*
    @Override
    public ResponceCustomerDto findById(Long customerId) {
        Customer customer = customerGateway.findById(customerId).orElse(null);
        ResponceCustomerDto responceCustomerDto = null;
        if (customer != null) {
            responceCustomerDto = Mapper.ConvertEntityToDto.convertCustomerToDto(customer);
        }
        return responceCustomerDto;
    }
 */

    /*private boolean validation(ChangeCustomerDto customerDto) {
        boolean exists1 = false;
        boolean exists2 = false;
        boolean exists3 = false;
        boolean exists4 = false;
        if (customerDto.mobileNumber() != null) {
            exists1 = customerGateway.existUserByMobileNumber(customerDto.mobileNumber());
        }
        if (customerDto.nationalCode() != null) {
            exists2 = customerGateway.existUserByNationalCode(customerDto.nationalCode());
        }
        if (customerDto.emailAddress() != null) {
            exists3 = customerGateway.existUserByEmailAddress(customerDto.emailAddress());
        }
        if (customerDto.userName() != null) {
            exists4 = customerGateway.existUserByUserName(customerDto.userName());
        }
        if (exists1 || exists2 || exists3 || exists4) {
            if (exists1)
                System.out.println("\u001B[31m" + "Expert with this Mobile Number is already exists" + "\u001B[0m");
            if (exists2)
                System.out.println("\u001B[31m" + "Expert with this National Code is already exists" + "\u001B[0m");
            if (exists3)
                System.out.println("\u001B[31m" + "Expert with this Email Address is already exists" + "\u001B[0m");
            if (exists4)
                System.out.println("\u001B[31m" + "Expert with this UserName is already exists" + "\u001B[0m");

            return true;
        }
        return false;
    }*/
   /* private void updateOperation(ChangeCustomerDto customerDto, Customer customer) {
        if (customer != null) {
            if (customerDto.firstName() != null) {
                customer.setFirstName(customerDto.firstName());
            }
            if (customerDto.lastName() != null) {
                customer.setLastName(customerDto.lastName());
            }
            if (customerDto.mobileNumber() != null) {
                customer.setMobileNumber(customerDto.mobileNumber());
            }
            if (customerDto.nationalCode() != null) {
                customer.setNationalCode(customerDto.nationalCode());
            }
            if (customerDto.emailAddress() != null) {
                customer.setEmailAddress(customerDto.emailAddress());
            }
            if (customerDto.userName() != null) {
                customer.setUserName(customerDto.userName());
            }
            customerGateway.save(customer);
        } else {
            System.out.println("Customer not found");
        }
    }*/