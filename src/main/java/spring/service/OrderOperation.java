package spring.service;

import spring.dto.RegisterOrderDto;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.enumaration.OrderStatus;

public interface OrderOperation {
    void orderRegister(RegisterOrderDto orderDto);
    Orders findById(Long orderId);
    void changeOrderStatus(Orders order,OrderStatus status);
    void addExpertToOrder(Orders order, Expert expert, OrderStatus status);
    void changeOrderStatusToStarted(Long orderId);
}
