package spring.service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.Suggestion;
import spring.enumaration.OrderStatus;
import spring.exception.NotFoundException;
import spring.exception.ValidationException;
import spring.exception.ViolationsException;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.repository.OrderGateway;
import spring.repository.SuggestionGateway;
import spring.service.OrderOperation;
import spring.service.SuggestionOperation;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SuggestionOperationImpl implements SuggestionOperation {
    private final SuggestionGateway suggestionGateway;
    private final ExpertGateway expertGateway;
    private final OrderOperation orderOperation;
    private final OrderGateway orderGateway;
    private final Validator validator;

    public Suggestion findById(Long suggestionId) {
        return suggestionGateway.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("no Suggestion Found "));
    }

    @Override
    public List<OrdersBriefProjection> listOrders(Long expertId) {
        List<OrdersBriefProjection> ordersBriefProjections = suggestionGateway.listOrders(expertId);
        if (ordersBriefProjections.isEmpty())
            throw new NotFoundException("no list Found for this expert");
        return ordersBriefProjections;
    }


    @Transactional
    public void registerSuggestion(RegisterSuggestionDto suggestionDto) {

        Expert expert = expertGateway.findById(suggestionDto.expertId())
                .orElse(null);
        Orders order = orderGateway.findById(suggestionDto.orderId())
                .orElse(null);

        validateInput(suggestionDto, expert, order);
        Suggestion suggestion = Mapper.ConvertDtoToEntity
                .convertSuggestionDtoToEntity(suggestionDto, expert, order);

        suggestionGateway.save(suggestion);
        if (order != null)
            if (order.getOrderStatus() != OrderStatus.WaitingForExpertSelection)
                orderOperation.changeOrderStatus(order, OrderStatus.WaitingForExpertSelection);

    }

    private void validateInput(RegisterSuggestionDto suggestionDto, Expert expert, Orders order) {
        Set<ConstraintViolation<RegisterSuggestionDto>> violations = validator.validate(suggestionDto);
        Set<String> errors = new HashSet<>();
        if (expert == null)
            errors.add("expert not Found");
        if (order == null)
            errors.add("order not Found");
        else {
            double basePrice = order.getSubService().getBasePrice();
            if (basePrice > suggestionDto.priceSuggestion())
                errors.add("your suggested price is less than the Base Price of this SubService");
            if (suggestionGateway.existsSuggestionByExpertAndOrder(expert, order))
                errors.add("the suggestion of this order for this expert are exists");
        }

        if (!violations.isEmpty() || !errors.isEmpty()) {
            for (ConstraintViolation<RegisterSuggestionDto> violation : violations) {
                errors.add(violation.getMessage());
            }

            throw new ValidationException(errors);
        }
    }

    @Override
    public List<SuggestionBriefProjection> listOrderSuggestions(OrderOfCustomerDto orderOfCustomerDto) {

        Set<ConstraintViolation<OrderOfCustomerDto>> violations = validator.validate(orderOfCustomerDto);
        if (!violations.isEmpty()) {
            throw new ViolationsException(violations);
        }

        List<SuggestionBriefProjection> suggestionBriefProjections = suggestionGateway
                .listOrderSuggestions(orderOfCustomerDto.customerId()
                        , orderOfCustomerDto.orderId());
        if (suggestionBriefProjections.isEmpty())
            throw new NotFoundException("no List Found for this customer and this order");
        return suggestionBriefProjections;
    }

    @Override
    public void selectSuggestionOfOrder(Long suggestionId) {
        Suggestion suggestion = suggestionGateway.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("suggestion not found"));
        Orders order = suggestion.getOrder();
        orderOperation.addExpertToOrder(order, suggestion.getExpert(),
                OrderStatus.WaitingForExpertToComeToYourPlace);
    }

}

/*
    @Transactional
    public void registerSuggestion(RegisterSuggestionDto suggestionDto) {
        Expert expert = expertGateway.findById(suggestionDto.expertId()).orElse(null);
        Orders order = orderGateway.findById(suggestionDto.orderId()).orElse(null);

        registerValidation(suggestionDto, expert, order);

        */
/*Expert expert = expertGateway.findById(suggestionDto.expertId())
                .orElseThrow(() -> new EntityNotFoundException("expert not Found"));
        Orders order = orderGateway.findById(suggestionDto.orderId())
                .orElseThrow(() -> new EntityNotFoundException("order not Found"));
        double basePrice = order.getSubService().getBasePrice();
        if (basePrice > suggestionDto.priceSuggestion())
            throw new PriceLessThanBasePriceException("your suggested price is less than " +
                                                      "the Base Price of this SubService");*//*

        Suggestion suggestion = Mapper.ConvertDtoToEntity
                .convertSuggestionDtoToEntity(suggestionDto, expert, order);
        */
/*if (suggestionGateway.existsSuggestionByExpertAndOrder(expert, order))
            throw new FoundException("the suggestion of this order for this expert are exists");*//*

        suggestionGateway.save(suggestion);
        orderOperation.changeOrderStatus(order, OrderStatus.WaitingForExpertSelection);


    }*/
