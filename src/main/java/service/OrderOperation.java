package service;

import dto.RegisterOrderDto;

public interface OrderOperation {
    void orderRegister(RegisterOrderDto orderDto);
}
