package spring.service;

import spring.dto.ChangeCustomerDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterCustomerDto;
import spring.dto.ResponceCustomerDto;
import spring.entity.Customer;

import java.util.List;

public interface CustomerOperation {
    void register(RegisterCustomerDto customerDto);
    void changePassword(ChangePasswordDto passwordDto);
    void update(ChangeCustomerDto customerDto);
    Customer findById(Long customerId);
    List<ResponceCustomerDto> findAllCustomers();
}
