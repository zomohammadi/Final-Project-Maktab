package spring.service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.Suggestion;
import spring.enumaration.OrderStatus;
import spring.exception.ValidationException;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.repository.OrderGateway;
import spring.repository.SuggestionGateway;
import spring.service.OrderOperation;
import spring.service.SuggestionOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SuggestionOperationImpl implements SuggestionOperation {
    private final SuggestionGateway suggestionGateway;
    private final ExpertGateway expertGateway;
    private final OrderOperation orderOperation;
    private final OrderGateway orderGateway;
    private final Validator validator;

    @Override
    public List<OrdersBriefProjection> listOrders(Long expertId) {
        return suggestionGateway.listOrders(expertId);
    }


    @Transactional
    public void registerSuggestion(RegisterSuggestionDto suggestionDto) {

        Expert expert = expertGateway.findById(suggestionDto.expertId()).orElse(null);
        Orders order = orderGateway.findById(suggestionDto.orderId()).orElse(null);

        registerValidation(suggestionDto, expert, order);

        Suggestion suggestion = Mapper.ConvertDtoToEntity
                .convertSuggestionDtoToEntity(suggestionDto, expert, order);

        suggestionGateway.save(suggestion);
        if (order != null)
            if (order.getOrderStatus() != OrderStatus.WaitingForExpertSelection)
                orderOperation.changeOrderStatus(order, OrderStatus.WaitingForExpertSelection);
    }

    private void registerValidation(RegisterSuggestionDto suggestionDto, Expert expert, Orders order) {
        Set<ConstraintViolation<RegisterSuggestionDto>> violations = validator.validate(suggestionDto);
        Set<String> errors = new HashSet<>();
        if (expert == null) errors.add("expert not Found");
        if (order == null) errors.add("order not Found");
        if (order != null) {
            double basePrice = order.getSubService().getBasePrice();
            if (basePrice > suggestionDto.priceSuggestion())
                errors.add("your suggested price is less than " +
                           "the Base Price of this SubService");
        }
        if (order != null && expert != null) {
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
