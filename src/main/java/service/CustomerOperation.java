package service;

import dto.RegisterCustomerDto;

public interface CustomerOperation {
    void register(RegisterCustomerDto customerDto);
}
