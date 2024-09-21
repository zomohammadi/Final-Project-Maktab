package service.Impl;

import dto.RegisterCustomerDto;
import entity.Credit;
import entity.Customer;
import enumaration.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.CustomerRepository;
import service.CustomerService;

import java.util.Set;

import static service.Impl.CheckInputInfoFromDB.checkInputInfoFromDB;

@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final Validator validator;


    @Override
    public void register(RegisterCustomerDto customerDto) {
        if (isNotValid(customerDto)) return;
        checkInputInfoFromDB("Customer", customerRepository.existUserByNationalCode(customerDto.nationalCode()),
                customerRepository.existUserByMobileNumber(customerDto.mobileNumber()),
                customerRepository.existUserByEmailAddress(customerDto.emailAddress()),
                customerRepository.existUserByUserName(customerDto.userName()));

        Customer customer = Mapper.convertCustomerDtoToEntity(customerDto);
        customer.setRole(Role.Customer);
        customer.setCredit(Credit.builder().build());

        customerRepository.save(customer);

    }

    private boolean isNotValid(RegisterCustomerDto customerDto) {
        Set<ConstraintViolation<RegisterCustomerDto>> violations = validator.validate(customerDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterCustomerDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return true;
        }
        return false;
    }


}
