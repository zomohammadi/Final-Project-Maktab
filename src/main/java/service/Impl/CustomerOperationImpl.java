package service.Impl;

import dto.RegisterCustomerDto;
import entity.Credit;
import entity.Customer;
import enumaration.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.CustomerGateway;
import service.CustomerOperation;

import java.util.Set;

import static service.Impl.CheckInputFromDBOperation.checkUserInfoFromDB;

@RequiredArgsConstructor
public class CustomerOperationImpl implements CustomerOperation {
    private final CustomerGateway customerRepository;
    private final Validator validator;


    @Override
    public void register(RegisterCustomerDto customerDto) {

        if (checkInputIsNotValid(customerDto)) return;
        Customer customer = Mapper.ConvertDtoToEntity.convertCustomerDtoToEntity(customerDto);
        customer.setRole(Role.Customer);
        customer.setCredit(Credit.builder().build());

        customerRepository.save(customer);
        System.out.println("customer register done");

    }

    private boolean checkInputIsNotValid(RegisterCustomerDto customerDto) {
        Set<ConstraintViolation<RegisterCustomerDto>> violations = validator.validate(customerDto);
        Set<String> customers = checkUserInfoFromDB("Customer",
                customerRepository.existUserByNationalCode(customerDto.nationalCode()),
                customerRepository.existUserByMobileNumber(customerDto.mobileNumber()),
                customerRepository.existUserByEmailAddress(customerDto.emailAddress()),
                customerRepository.existUserByUserName(customerDto.userName()));
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


}
