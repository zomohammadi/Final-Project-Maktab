package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.dto.RegisterOrderDto;
import spring.entity.Customer;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;
import spring.enumaration.OrderStatus;
import spring.exception.NotFoundException;
import spring.mapper.Mapper;
import spring.repository.CustomerGateway;
import spring.repository.OrderGateway;
import spring.repository.SubServiceGateway;
import spring.service.OrderOperation;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class OrderOperationImpl implements OrderOperation {
    private final OrderGateway orderGateway;
    private final SubServiceGateway subServiceGateway;
    private final CustomerGateway customerGateway;
    private final Validator validator;

    @Override
    public void orderRegister(RegisterOrderDto orderDto) {
        Set<ConstraintViolation<RegisterOrderDto>> violations = validator.validate(orderDto);
        SubService subService = subServiceGateway.findById(orderDto.subServiceId()).orElse(null);
        Customer customer = customerGateway.findById(orderDto.customerId()).orElse(null);
        if (!violations.isEmpty() || subService == null || customer == null) {
            for (ConstraintViolation<RegisterOrderDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (subService == null)
                System.out.println("\u001B[31m" + "subService not found" + "\u001B[0m");
            if (customer == null)
                System.out.println("\u001B[31m" + "Customer not found" + "\u001B[0m");
            if (subService != null && subService.getBasePrice() > orderDto.priceSuggested())
                System.out.println("\u001B[31m" + """
                        your suggested price is less than the Base Price of this SubService
                        """ + "\u001B[0m");
            return;
        }
        // if (subService!=null && customer !=null) {
        if (subService.getBasePrice() > orderDto.priceSuggested()) {
            System.err.println("""
                    your suggested price is less than the Base Price of this SubService
                    """);
            return;
        }
        Orders order = Mapper.ConvertDtoToEntity.
                convertOrderDtoToEntity(orderDto, customer, subService);
        order.setOrderStatus(OrderStatus.WaitingForSuggestionOfExperts);
        orderGateway.save(order);
        System.out.println("Order Register Done");
        //  }
    }

    @Override
    public Orders findById(Long orderId) {
        return orderGateway
                .findById(orderId).orElseThrow(() -> new EntityNotFoundException("order not Found"));

    }

    public void changeOrderStatus(Orders order, OrderStatus status) {
        order.setOrderStatus(status);
        orderGateway.save(order);
    }

    public void addExpertToOrder(Orders order, Expert expert, OrderStatus status) {
        order.setExpert(expert);
        changeOrderStatus(order, status);
    }

    public void changeOrderStatusToStarted(Long orderId) {
        Orders order = orderGateway.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order not Found"));
        if (order.getOrderStatus() == OrderStatus.WaitingForExpertToComeToYourPlace)
            changeOrderStatus(order, OrderStatus.Started);
        else throw new NotFoundException("your status is not Waiting For Expert To Come To YourPlace ");
    }
    public void changeOrderStatusToDone(Long orderId) {
        Orders order = orderGateway.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order not Found"));
        if (order.getOrderStatus() == OrderStatus.Started)
            changeOrderStatus(order, OrderStatus.Done);
        else throw new NotFoundException("your status is Started");
    }

}

