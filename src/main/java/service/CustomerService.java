package service;

import dto.RegisterCustomerDto;
import entity.Customer;

public interface CustomerService {
    void register(RegisterCustomerDto customerDto);
}
