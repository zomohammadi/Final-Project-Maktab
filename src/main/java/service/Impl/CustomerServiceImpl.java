package service.Impl;

import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import entity.Credit;
import entity.Customer;
import entity.Expert;
import enumaration.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import mapper.Mapper;
import repository.BaseEntityRepository;
import service.CustomerService;

import java.util.Set;

public class CustomerServiceImpl implements CustomerService {
    private final BaseEntityRepository<Customer> customerBaseEntityRepository;
    private final Validator validator;

    public CustomerServiceImpl(BaseEntityRepository<Customer> customerBaseEntityRepository, Validator validator) {
        this.customerBaseEntityRepository = customerBaseEntityRepository;
        this.validator = validator;
    }

    @Override
    public void register(RegisterCustomerDto customerDto) {
        Set<ConstraintViolation<RegisterCustomerDto>> violations = validator.validate(customerDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterCustomerDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return;
        }
        Customer customer = Mapper.convertCustomerDtoToEntity(customerDto);
        customer.setRole(Role.Customer);
        customer.setCredit(Credit.builder().build());

        customerBaseEntityRepository.save(customer);

    }
}
