package spring.service;

import spring.dto.RegisterOrderDto;

public interface OrderOperation {
    void orderRegister(RegisterOrderDto orderDto);
}
