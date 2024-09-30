package spring.service;

import spring.dto.RegisterOrderDto;
import spring.entity.Orders;
import spring.enumaration.OrderStatus;

public interface OrderOperation {
    void orderRegister(RegisterOrderDto orderDto);
    Orders findById(Long orderId);
    void changeOrderStatus(Orders oldOrder,OrderStatus status);
}
