package service;

import dto.ChangePasswordDto;
import dto.RegisterCustomerDto;

public interface CustomerOperation {
    void register(RegisterCustomerDto customerDto);
    void changePassword(ChangePasswordDto passwordDto);
}
