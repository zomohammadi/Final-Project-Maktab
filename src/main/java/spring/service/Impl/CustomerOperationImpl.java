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

        Set<String> errors = CheckInputFromDBOperation.checkUserInfoFromDB("Customer",
                customerGateway.existUserByNationalCode(customerDto.nationalCode()),
                customerGateway.existUserByMobileNumber(customerDto.mobileNumber()),
                customerGateway.existUserByEmailAddress(customerDto.emailAddress()),
                customerGateway.existUserByUserName(customerDto.userName()));
        if (!errors.isEmpty())
            throw new EntityExistsException(String.join(", ", errors));

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