package service.Impl;

import dto.ChangeCustomerDto;
import dto.ChangePasswordDto;
import dto.RegisterCustomerDto;
import dto.ResponceCustomerDto;
import entity.Credit;
import entity.Customer;
import enumaration.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import org.mindrot.jbcrypt.BCrypt;
import repository.CustomerGateway;
import service.CustomerOperation;

import java.util.Set;

import static service.Impl.CheckInputFromDBOperation.checkUserInfoFromDB;

@RequiredArgsConstructor
public class CustomerOperationImpl implements CustomerOperation {
    private final CustomerGateway customerGateway;
    private final Validator validator;

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public void register(RegisterCustomerDto customerDto) {

        if (checkInputIsNotValid(customerDto)) return;
        Customer customer = Mapper.ConvertDtoToEntity.convertCustomerDtoToEntity(customerDto);
        customer.setRole(Role.Customer);
        customer.setCredit(Credit.builder().build());
        customer.setPassword(hashPassword(customerDto.password()));
        customerGateway.save(customer);
        System.out.println("customer register done");

    }

    private boolean checkInputIsNotValid(RegisterCustomerDto customerDto) {
        Set<ConstraintViolation<RegisterCustomerDto>> violations = validator.validate(customerDto);
        Set<String> customers = checkUserInfoFromDB("Customer",
                customerGateway.existUserByNationalCode(customerDto.nationalCode()),
                customerGateway.existUserByMobileNumber(customerDto.mobileNumber()),
                customerGateway.existUserByEmailAddress(customerDto.emailAddress()),
                customerGateway.existUserByUserName(customerDto.userName()));
        if (!violations.isEmpty() || !customers.isEmpty()) {
            for (ConstraintViolation<RegisterCustomerDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (!customers.isEmpty()) {
                for (String s : customers)
                    System.out.println("\u001B[31m" + s + "\u001B[0m");
            }
            return true;
        }
        return false;
    }

    public void changePassword(ChangePasswordDto passwordDto) {
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(passwordDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<ChangePasswordDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return;
        }
        Customer customer = customerGateway.findById(passwordDto.userId());
        if (customer != null) {
            customer.setPassword(hashPassword(passwordDto.password()));
            customerGateway.update(customer);
            System.out.println("change password done");
        } else System.err.println("Customer not found");
    }

    @Override
    public void update(ChangeCustomerDto customerDto) {
        if (validation(customerDto)) return;
        Customer customer1 = Mapper.ConvertDtoToEntity.convertChangeCustomerDtoToEntity(customerDto);
        Customer customer = customerGateway.findById(customer1.getId());
        updateOperation(customerDto, customer);
    }

    private boolean validation(ChangeCustomerDto customerDto) {
        Set<ConstraintViolation<ChangeCustomerDto>> violations = validator.validate(customerDto);
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
        if (!violations.isEmpty() || exists1 || exists2 || exists3 || exists4) {
            for (ConstraintViolation<ChangeCustomerDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
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
    }

    private void updateOperation(ChangeCustomerDto customerDto, Customer customer) {
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
            customerGateway.update(customer);
            System.out.println("Customer Updated!");
        } else {
            System.out.println("Customer not found");
        }
    }
    @Override
    public ResponceCustomerDto findById(Long customerId) {
        Customer customer = customerGateway.findById(customerId);
        ResponceCustomerDto responceCustomerDto = null;
        if (customer != null) {
            responceCustomerDto = Mapper.ConvertEntityToDto.convertCustomerToDto(customer);
        }
        return responceCustomerDto;
    }

}
