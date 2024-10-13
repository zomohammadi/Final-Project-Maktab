package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;
import spring.entity.Suggestion;
import spring.enumaration.OrderStatus;
import spring.exception.ValidationException;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.repository.OrderGateway;
import spring.repository.SuggestionGateway;
import spring.service.OrderOperation;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuggestionOperationTest {
    @InjectMocks
    private SuggestionOperationImpl underTest;
    @Mock
    private SuggestionGateway suggestionGateway;

    @Mock
    private ExpertGateway expertGateway;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private OrderOperation orderOperation;

    @Mock
    private Validator validator;

//----
/*@BeforeEach
void setUp() {
    when(validator.validate(any())).thenReturn(Collections.emptySet());
}*/

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }*/

    //Test for findById Method:

    @Test
    void canFindById() {

        when(suggestionGateway.findById(anyLong()))
                .thenReturn(Optional.of(
                        new Suggestion()
                ));
        Suggestion actual = underTest.findById(1L);
    }


    @Test
    void canNotFindById() {
        assertThrowsExactly(
                EntityNotFoundException.class,
                () -> underTest.findById(1L)
        );
    }

    //Test for listOrders Method:

    @Test
    void testListOrders_whenOrdersExist() {

        Long expertId = 1L;
       // OrdersBriefProjection mockOrder = mock(OrdersBriefProjection.class);
        // List<OrdersBriefProjection> mockOrders = List.of(mockOrder);
        List<OrdersBriefProjection> mockOrders = mock(List.class);
        when(mockOrders.size()).thenReturn(1);
        when(suggestionGateway.listOrders(expertId)).thenReturn(mockOrders);

        List<OrdersBriefProjection> result = underTest.listOrders(expertId);

        assertEquals(1, result.size());
        verify(suggestionGateway, times(1)).listOrders(expertId);
    }

    @Test
    void testListOrders_whenNoOrdersFound() {

        Long expertId = 1L;
        when(suggestionGateway.listOrders(expertId)).thenReturn(Collections.emptyList());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.listOrders(expertId);
        });

    }

    //Test for registerSuggestion Method:


    @Test
    void testRegisterSuggestion_MissingExpert() {
        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(5000000.0)
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12, 8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        when(expertGateway.findById(expertId)).thenReturn(Optional.empty()); // Expert not found

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.registerSuggestion(suggestionDto);
        });
        assertTrue(exception.getMessage().contains("expert not Found"));
    }

    @Test
    void testRegisterSuggestion_MissingOrder() {
        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(5000000.0)
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12, 8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        Expert expert = new Expert();//mock(Expert.class);
        when(expertGateway.findById(expertId)).thenReturn(Optional.of(expert));
        when(orderGateway.findById(orderId)).thenReturn(Optional.empty()); // Order not found

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.registerSuggestion(suggestionDto);
        });
        assertTrue(exception.getMessage().contains("order not Found"));


    }

    @Test
    void testRegisterSuggestion_PriceBelowBasePrice() {
        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(300.0) // Price is below base price
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12,
                        8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        Expert expert = mock(Expert.class);
        Orders order = mock(Orders.class);
        SubService subService = mock(SubService.class);

        when(expertGateway.findById(expertId)).thenReturn(Optional.of(expert));
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getSubService()).thenReturn(subService);
        when(subService.getBasePrice()).thenReturn(400.0); // Base price is higher than suggested price

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.registerSuggestion(suggestionDto);
        });
        assertTrue(exception.getMessage().contains("your suggested price is less than the Base Price of this SubService"));

    }

    @Test
    void testRegisterSuggestion_ExistingSuggestion() {
        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(5000000.0)
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12, 8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        Expert expert = mock(Expert.class);
        Orders order = mock(Orders.class);
        SubService subService = mock(SubService.class);

        when(expertGateway.findById(expertId)).thenReturn(Optional.of(expert));
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getSubService()).thenReturn(subService);
        when(subService.getBasePrice()).thenReturn(400.0);
        when(suggestionGateway.existsSuggestionByExpertAndOrder(expert, order)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.registerSuggestion(suggestionDto);
        });

        assertTrue(exception.getMessage().contains("the suggestion of this order for this expert are exists"));
    }

    @Test
    void testRegisterSuggestion_Success() {

        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(5000000.0)
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12,
                        8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        Expert expert = mock(Expert.class);
        Orders order = mock(Orders.class);

        when(expertGateway.findById(expertId)).thenReturn(Optional.of(expert));
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        SubService subService = mock(SubService.class);
        when(order.getSubService()).thenReturn(subService);
        when(subService.getBasePrice()).thenReturn(400.0);

        Set<ConstraintViolation<RegisterSuggestionDto>> violations = Collections.emptySet();
        when(validator.validate(suggestionDto)).thenReturn(violations);

        Suggestion suggestion = mock(Suggestion.class);

        /*Suggestion entity = Mapper.ConvertDtoToEntity.convertSuggestionDtoToEntity(suggestionDto, expert, order);
        when(entity)
                .thenReturn(suggestion);*/

       /* PowerMockito.mockStatic(Mapper.ConvertDtoToEntity.class);
        BDDMockito.given(Mapper.ConvertDtoToEntity.convertSuggestionDtoToEntity(suggestionDto, expert, order))
                .willReturn(suggestion);*/
        try (MockedStatic<Mapper.ConvertDtoToEntity> mockedMapper = Mockito.mockStatic(Mapper.ConvertDtoToEntity.class)) {
            mockedMapper.when(() -> Mapper.ConvertDtoToEntity.convertSuggestionDtoToEntity(suggestionDto, expert, order))
                    .thenReturn(suggestion);

            underTest.registerSuggestion(suggestionDto);

        }

    }


    @Test
    void testRegisterSuggestion_WithValidationViolations() {
        Long expertId = 1L;
        Long orderId = 1L;
        RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                .expertId(expertId)
                .orderId(orderId)
                .priceSuggestion(5000000.0)
                .durationOfService("5 saat")
                .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12,
                        8, 31, 0, 0, ZonedDateTime.now().getZone()))
                .build();

        Expert expert = mock(Expert.class);
        Orders order = mock(Orders.class);

        when(expertGateway.findById(expertId)).thenReturn(Optional.of(expert));
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));

        SubService subService = mock(SubService.class);
        when(order.getSubService()).thenReturn(subService);
        when(subService.getBasePrice()).thenReturn(400.0);

        Set<ConstraintViolation<RegisterSuggestionDto>> violations = new HashSet<>();
        ConstraintViolation<RegisterSuggestionDto> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("validation error");
        violations.add(violation);

        when(validator.validate(suggestionDto)).thenReturn(violations);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            underTest.registerSuggestion(suggestionDto);
        });

        assertTrue(exception.getMessage().contains("validation error"));
    }
    //Test for listOrderSuggestions  Method:


    @Test
    void testListOrderSuggestions_ValidDto_ReturnsSuggestions() {
        OrderOfCustomerDto validDto = new OrderOfCustomerDto(1L, 2L);
        SuggestionBriefProjection suggestion = mock(SuggestionBriefProjection.class);
        when(suggestionGateway.listOrderSuggestions(validDto.customerId(), validDto.orderId()))
                .thenReturn(List.of(suggestion));

        List<SuggestionBriefProjection> result = underTest.listOrderSuggestions(validDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(suggestion, result.get(0));
    }

    @Test
    void testListOrderSuggestions_ValidationViolations_ThrowsViolationsException() {

        OrderOfCustomerDto invalidDto = new OrderOfCustomerDto(null, null);
        Set<ConstraintViolation<OrderOfCustomerDto>> violations = new HashSet<>();
        ConstraintViolation<OrderOfCustomerDto> violation = mock(ConstraintViolation.class);
//        when(violation.getMessage()).thenReturn("Invalid customer ID");
        violations.add(violation);

        when(validator.validate(invalidDto)).thenReturn(violations);

      /*  ViolationsException exception = assertThrows(ViolationsException.class, () -> {
            underTest.listOrderSuggestions(invalidDto);
        });*/

       /* assertEquals(violations, exception.getViolations());*/
    }

    @Test
    void testListOrderSuggestions_NoSuggestionsFound_ThrowsNotFoundException() {
        OrderOfCustomerDto validDto = new OrderOfCustomerDto(1L, 2L);
        when(validator.validate(validDto)).thenReturn(Collections.emptySet());
        when(suggestionGateway.listOrderSuggestions(validDto.customerId(), validDto.orderId()))
                .thenReturn(Collections.emptyList());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.listOrderSuggestions(validDto);
        });

        assertEquals("no List Found for this customer and this order", exception.getMessage());
    }

    //---------Test For selectSuggestionOfOrder
    @Test
    void testSelectSuggestionOfOrder_Success() {
        Long suggestionId = 1L;
        Suggestion suggestion = mock(Suggestion.class);
        Orders order = mock(Orders.class);
        Expert expert = mock(Expert.class);

        when(suggestionGateway.findById(suggestionId)).thenReturn(Optional.of(suggestion));
        when(suggestion.getOrder()).thenReturn(order);
        when(suggestion.getExpert()).thenReturn(expert);

        underTest.selectSuggestionOfOrder(suggestionId);

        verify(orderOperation).addExpertToOrder(order, expert, OrderStatus.WaitingForExpertToComeToYourPlace);
    }

    @Test
    void testSelectSuggestionOfOrder_NotFound() {
        Long suggestionId = 1L;

        when(suggestionGateway.findById(suggestionId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.selectSuggestionOfOrder(suggestionId);
        });

        assertEquals("suggestion not found", exception.getMessage());
    }
}
