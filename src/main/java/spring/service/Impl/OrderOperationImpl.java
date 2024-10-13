package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.RegisterOrderDto;
import spring.entity.Customer;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;
import spring.enumaration.OrderStatus;
import spring.exception.NotFoundException;
import spring.mapper.Mapper;
import spring.repository.OrderGateway;
import spring.service.CustomerOperation;
import spring.service.OrderOperation;
import spring.service.SubServiceOperation;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderOperationImpl implements OrderOperation {
    private final OrderGateway orderGateway;
    private final SubServiceOperation subServiceOperation;
    private final CustomerOperation customerOperation;

    @Override
    @Transactional
    public void orderRegister(RegisterOrderDto orderDto) {


        SubService subService = subServiceOperation.findById(orderDto.subServiceId());
        Customer customer = customerOperation.findById(orderDto.customerId());
        validateInput(orderDto, subService, customer);
        Orders order = Mapper.ConvertDtoToEntity.
                convertOrderDtoToEntity(orderDto, customer, subService);
        order.setOrderStatus(OrderStatus.WaitingForSuggestionOfExperts);
        orderGateway.save(order);
    }

    private void validateInput(RegisterOrderDto orderDto, SubService subService, Customer customer) {
        Set<String> errors = new HashSet<>();
        if (subService == null)
            errors.add("subService not Found");
        if (customer == null)
            errors.add("Customer not Found");
        if (subService != null && subService.getBasePrice() > orderDto.priceSuggested())
            errors.add(" your suggested price is less than the Base Price of this SubService");
        if (!errors.isEmpty()) {
            throw new EntityNotFoundException((String.join(", ", errors)));
        }
    }


    @Override
    public Orders findById(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");
        return orderGateway
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order not Found"));

    }

    @Override
    @Transactional
    public void changeOrderStatus(Orders order, OrderStatus status) {
        order.setOrderStatus(status);
        orderGateway.save(order);
    }

    public void addExpertToOrder(Orders order, Expert expert, OrderStatus status) {
        order.setExpert(expert);
        changeOrderStatus(order, status);
    }

    public void changeOrderStatusToStarted(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");
        Orders order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.WaitingForExpertToComeToYourPlace))
            changeOrderStatus(order, OrderStatus.Started);
        else
            throw new NotFoundException("your status is not Waiting For Expert To Come To YourPlace");
    }

    public void changeOrderStatusToDone(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");
        Orders order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.Started))
            changeOrderStatus(order, OrderStatus.Done);
        else
            throw new NotFoundException("your status is Started");
    }

}

