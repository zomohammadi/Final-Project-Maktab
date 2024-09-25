package service;

import dto.*;

public interface CustomerOperation {
    void register(RegisterCustomerDto customerDto);
    void changePassword(ChangePasswordDto passwordDto);
    void update(ChangeCustomerDto customerDto);
    ResponceCustomerDto findById(Long customerId);
}
