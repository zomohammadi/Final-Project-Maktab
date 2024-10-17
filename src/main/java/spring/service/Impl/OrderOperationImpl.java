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
import spring.exception.ValidationException;
import spring.mapper.Mapper;
import spring.repository.OrderGateway;
import spring.service.CustomerOperation;
import spring.service.OrderOperation;
import spring.service.SubServiceOperation;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        if (subService != null && subService.getBasePrice() > orderDto.priceSuggested())
            throw new ValidationException(Set.of("your suggested price is less than the Base Price of this SubService"));
        Orders order = Mapper.ConvertDtoToEntity.
                convertOrderDtoToEntity(orderDto, customer, subService);
        order.setOrderStatus(OrderStatus.WaitingForSuggestionOfExperts);
        orderGateway.save(order);
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
    //@Transactional
    public void changeOrderStatus(Orders order, OrderStatus status) {
        order.setOrderStatus(status);
        orderGateway.save(order);
    }

    public void addExpertToOrder(Orders order, Expert expert, OrderStatus status) {
        order.setExpert(expert);
        changeOrderStatus(order, status);
    }

    @Transactional
    public void changeOrderStatusToStarted(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");
        Orders order = findById(orderId);
        if (!order.getOrderStatus().equals(OrderStatus.WaitingForExpertToComeToYourPlace))
            throw new IllegalStateException("your status is not Waiting For Expert To Come To YourPlace");
        changeOrderStatus(order, OrderStatus.Started);
    }


    // @Override//------------t-o-d-o
    // @Transactional
    public void changeOrderStatusToPaid(Orders order) {
        if (!order.getOrderStatus().equals(OrderStatus.Done))
            throw new IllegalStateException("Order is not in 'DONE' state");
        changeOrderStatus(order, OrderStatus.Paid);


    }

    public void changeOrderStatusToDone(Orders order) {
        if (!order.getOrderStatus().equals(OrderStatus.Started))
            throw new IllegalStateException("your status is not Started");

        order.setTimeServiceCompleted(/*ZonedDateTime.now()*/
                ZonedDateTime.of(2024, 10, 17, 23,
                        30, 0, 0, ZoneId.of("UTC"))

                /*ZonedDateTime.of(2024, 10, 19, 6,
                        0, 0, 0, ZoneId.of("UTC"))*/);

        changeOrderStatus(order, OrderStatus.Done);
    }

    @Override
    public int getCountOfExpertOperationPerSubService(Expert expert, SubService subService) {
        return orderGateway.countByExpertAndSubService(expert, subService);
    }
}

