package spring.service.Impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.dto.RegisterOrderDto;
import spring.entity.Customer;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;
import spring.enumaration.OrderStatus;
import spring.exception.NotFoundException;
import spring.exception.ValidationException;
import spring.repository.CustomerGateway;
import spring.repository.OrderGateway;
import spring.repository.SubServiceGateway;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderOperationImplTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private SubServiceGateway subServiceGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private Validator validator;

    @InjectMocks
    private OrderOperationImpl underTest;

    //Test Method for orderRegister
    @Test
    void orderRegister_validInput_shouldSaveOrder() {
        RegisterOrderDto orderDto =
                RegisterOrderDto.builder()
                        .customerId(2L)
                        .subServiceId(2L)
                        .priceSuggested(5000000.0)
                        .address("fdsgdfs 3434 34")
                        .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31
                                , 0, 0, ZonedDateTime.now().getZone()))
                        .build();
        SubService subService = new SubService();
        subService.setBasePrice(500.0);
        Customer customer = new Customer();

        Set<ConstraintViolation<RegisterOrderDto>> violations = new HashSet<>();
        when(validator.validate(any(RegisterOrderDto.class))).thenReturn(violations);
        when(subServiceGateway.findById(orderDto.subServiceId())).thenReturn(Optional.of(subService));
        when(customerGateway.findById(orderDto.customerId())).thenReturn(Optional.of(customer));

        underTest.orderRegister(orderDto);

        ArgumentCaptor<Orders> orderCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderGateway, times(1)).save(orderCaptor.capture());
        Orders savedOrder = orderCaptor.getValue();

        assertEquals(OrderStatus.WaitingForSuggestionOfExperts, savedOrder.getOrderStatus());
        assertEquals(customer, savedOrder.getCustomer());
        assertEquals(subService, savedOrder.getSubService());
        assertEquals(orderDto.priceSuggested(), savedOrder.getPriceSuggested());
    }

    @Test
    void orderRegister_subServiceNotFound_shouldThrowValidationException() {
        RegisterOrderDto orderDto =
                RegisterOrderDto.builder()
                        .customerId(2L)
                        .subServiceId(2L)
                        .priceSuggested(5000000.0)
                        .address("fdsgdfs 3434 34")
                        // .serviceDescription("2324jjjjjjjjjjjjjjjjj")
                        .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31
                                , 0, 0, ZonedDateTime.now().getZone()))
                        .build();
        when(subServiceGateway.findById(orderDto.subServiceId())).thenReturn(Optional.empty());
        when(customerGateway.findById(orderDto.customerId())).thenReturn(Optional.of(new Customer()));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.orderRegister(orderDto);
        });

        assertTrue(exception.getErrors().contains("subService not Found"));
        verify(orderGateway, never()).save(any());
    }

    @Test
    void orderRegister_customerNotFound_shouldThrowValidationException() {
        RegisterOrderDto orderDto = RegisterOrderDto.builder()
                .customerId(2L)
                .subServiceId(2L)
                .priceSuggested(5000000.0)
                .address("fdsgdfs 3434 34")
                .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        SubService subService = new SubService();
        subService.setBasePrice(500.0);  // Ensure basePrice is set to avoid NullPointerException

        when(subServiceGateway.findById(orderDto.subServiceId())).thenReturn(Optional.of(subService));
        when(customerGateway.findById(orderDto.customerId())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.orderRegister(orderDto);
        });

        assertTrue(exception.getErrors().contains("Customer not Found"));
        verify(orderGateway, never()).save(any());
    }

    @Test
    void orderRegister_suggestedPriceLessThanBasePrice_shouldThrowValidationException() {

        RegisterOrderDto orderDto = RegisterOrderDto.builder()
                .customerId(2L)
                .subServiceId(2L)
                .priceSuggested(400.0)
                .address("fdsgdfs 3434 34")
                // .serviceDescription("2324jjjjjjjjjjjjjjjjj")
                .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31
                        , 0, 0, ZonedDateTime.now().getZone()))
                .build();
        SubService subService = new SubService();
        subService.setBasePrice(500.0);
        Customer customer = new Customer();

        when(subServiceGateway.findById(orderDto.subServiceId())).thenReturn(Optional.of(subService));
        when(customerGateway.findById(orderDto.customerId())).thenReturn(Optional.of(customer));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.orderRegister(orderDto);
        });

        assertTrue(exception.getErrors().contains(" your suggested price is less than the Base Price of this SubService"));
        verify(orderGateway, never()).save(any());
    }

    @Test
    void orderRegister_withViolations_shouldThrowValidationException() {

        RegisterOrderDto orderDto = RegisterOrderDto.builder()
                .customerId(2L)
                .subServiceId(2L)
                .priceSuggested(600.0)
                .address("") // Empty address to trigger validation violation
                .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();
        SubService subService = new SubService();
        subService.setBasePrice(500.0);
        Customer customer = new Customer();

        ConstraintViolation<RegisterOrderDto> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Address must not be blank");

        Set<ConstraintViolation<RegisterOrderDto>> violations = new HashSet<>();
        violations.add(violation);
        when(validator.validate(any(RegisterOrderDto.class))).thenReturn(violations);

        when(subServiceGateway.findById(orderDto.subServiceId())).thenReturn(Optional.of(subService));
        when(customerGateway.findById(orderDto.customerId())).thenReturn(Optional.of(customer));


        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.orderRegister(orderDto);
        });

        assertTrue(exception.getErrors().contains("Address must not be blank"));
        verify(orderGateway, never()).save(any());
    }

    //Test for findById Method:

    @Test
    void canFindById() {

        when(orderGateway.findById(anyLong()))
                .thenReturn(Optional.of(
                        new Orders()
                ));
        Orders actual = underTest.findById(1L);
    }


    @Test
    void canNotFindById() {
        assertThrowsExactly(
                EntityNotFoundException.class,
                () -> underTest.findById(1L)
        );
    }

    //Test Method for changeOrderStatus
    @Test
    void changeOrderStatus_shouldUpdateOrderStatusAndSave() {
        Orders order = new Orders();
        OrderStatus newStatus = OrderStatus.Started;

        underTest.changeOrderStatus(order, newStatus);

        assertEquals(newStatus, order.getOrderStatus());
        verify(orderGateway, times(1)).save(order);
    }

    //Test For addExpertToOrder
    @Test
    void addExpertToOrder_shouldSetExpertAndChangeOrderStatus() {
        Orders order = new Orders();
        Expert expert = new Expert();
        OrderStatus newStatus = OrderStatus.WaitingForExpertToComeToYourPlace;

        underTest.addExpertToOrder(order, expert, newStatus);

        assertEquals(expert, order.getExpert());
        verify(orderGateway, times(1)).save(order);
    }


    //test  for changeOrderStatusToStarted

    @Test
    void changeOrderStatusToStarted_validStatus_shouldChangeStatus() {

        Long orderId = 1L;
        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.WaitingForExpertToComeToYourPlace);

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        underTest.changeOrderStatusToStarted(orderId);

        verify(orderGateway, times(1)).findById(orderId);
        verify(orderGateway, times(1)).save(order);
        assertEquals(OrderStatus.Started, order.getOrderStatus());
    }

    @Test
    void changeOrderStatusToStarted_invalidStatus_shouldThrowNotFoundException() {
        Long orderId = 2L;
        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.WaitingForExpertSelection); // Not the expected status

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            underTest.changeOrderStatusToStarted(orderId);
        });

        assertEquals("your status is not Waiting For Expert To Come To YourPlace", exception.getMessage());
        verify(orderGateway, times(1)).findById(orderId);
        verify(orderGateway, never()).save(any());
    }

    @Test
    void changeOrderStatusToStarted_orderNotFound_shouldThrowEntityNotFoundException() {
        Long orderId = 3L;

        when(orderGateway.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            underTest.changeOrderStatusToStarted(orderId);
        });

        verify(orderGateway, times(1)).findById(orderId);
        verify(orderGateway, never()).save(any());
    }

    //test method for changeOrderStatusToDone:

    @Test
    void changeOrderStatusToDone_validStatus_shouldChangeToDone() {
        Long orderId = 1L;
        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.Started);

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        underTest.changeOrderStatusToDone(orderId);

        assertEquals(OrderStatus.Done, order.getOrderStatus());
        verify(orderGateway, times(1)).save(order);
    }

    @Test
    void changeOrderStatusToDone_invalidStatus_shouldThrowNotFoundException() {
        Long orderId = 2L;
        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.WaitingForExpertToComeToYourPlace);

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            underTest.changeOrderStatusToDone(orderId);
        });

        assertEquals("your status is Started", exception.getMessage());
        verify(orderGateway, times(1)).findById(orderId);
        verify(orderGateway, never()).save(any());
    }

    @Test
    void changeOrderStatusToDone_orderNotFound_shouldThrowEntityNotFoundException() {
        Long orderId = 3L;

        when(orderGateway.findById(orderId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.changeOrderStatusToDone(orderId);
        });

        assertEquals("order not Found", exception.getMessage());
        verify(orderGateway, times(1)).findById(orderId);
        verify(orderGateway, never()).save(any());
    }

}