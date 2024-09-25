package service.Impl;

import dto.ChangePasswordDto;
import dto.RegisterCustomerDto;
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

}
