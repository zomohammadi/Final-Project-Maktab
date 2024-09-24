package service.Impl;

import dto.RegisterOrderDto;
import entity.Customer;
import entity.Orders;
import entity.SubService;
import enumaration.OrderStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.CustomerGateway;
import repository.OrderGateway;
import repository.SubServiceGateway;
import service.OrderOperation;

import java.util.Set;

@RequiredArgsConstructor
public class OrderOperationImpl implements OrderOperation {
    private final OrderGateway orderGateway;
    private final SubServiceGateway subServiceGateway;
    private final CustomerGateway customerGateway;
    private final Validator validator;

    @Override
    public void orderRegister(RegisterOrderDto orderDto) {
        if (isNotValidate(orderDto)) return;
        SubService subService = subServiceGateway.findById(orderDto.subServiceId());
        Customer customer = customerGateway.findById(orderDto.customerId());
        if (customer != null && subService != null) {
            if (subService.getBasePrice() < orderDto.priceSuggested()) {
                System.err.println("""
                        your suggested price is less than
                        the Base Price of this SubService
                        """);
                return;
            }
            Orders order = Mapper.ConvertDtoToEntity.
                    convertOrderDtoToEntity(orderDto, customer, subService);
            order.setOrderStatus(OrderStatus.WaitingForSuggestionOfExperts);
            orderGateway.save(order);
            System.out.println("Order Register Done");
        } else {
            if (customer == null)
                System.err.println("customer not found");
            if (subService == null)
                System.err.println("subService not found");
        }
    }

    private boolean isNotValidate(RegisterOrderDto orderDto) {
        Set<ConstraintViolation<RegisterOrderDto>> violations = validator.validate(orderDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterOrderDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return true;
        }
        return false;
    }

}

