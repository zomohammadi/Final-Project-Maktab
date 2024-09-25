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
        Set<ConstraintViolation<RegisterOrderDto>> violations = validator.validate(orderDto);
        SubService subService = subServiceGateway.findById(orderDto.subServiceId());
        Customer customer = customerGateway.findById(orderDto.customerId());
        if (!violations.isEmpty() || subService==null || customer==null) {
            for (ConstraintViolation<RegisterOrderDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (subService==null)
                System.out.println("\u001B[31m" + "subService not found" + "\u001B[0m");
            if (customer==null)
                System.out.println("\u001B[31m" + "Customer not found" + "\u001B[0m");
            if (subService!=null && subService.getBasePrice() > orderDto.priceSuggested())
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



}

