package spring.service;

import spring.dto.ChangeCustomerDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterCustomerDto;
import spring.dto.ResponceCustomerDto;

public interface CustomerOperation {
    void register(RegisterCustomerDto customerDto);
    void changePassword(ChangePasswordDto passwordDto);
    void update(ChangeCustomerDto customerDto);
    ResponceCustomerDto findById(Long customerId);
}
