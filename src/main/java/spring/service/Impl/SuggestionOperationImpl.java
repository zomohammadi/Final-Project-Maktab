package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.Suggestion;
import spring.enumaration.OrderStatus;
import spring.exception.ValidationException;
import spring.mapper.Mapper;
import spring.repository.SuggestionGateway;
import spring.service.ExpertOperation;
import spring.service.OrderOperation;
import spring.service.SuggestionOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SuggestionOperationImpl implements SuggestionOperation {
    private final SuggestionGateway suggestionGateway;
    private final OrderOperation orderOperation;
    private final ExpertOperation expertOperation;

    public Suggestion findById(Long suggestionId) {
        return suggestionGateway.findById(suggestionId)
                .orElseThrow(() -> new EntityNotFoundException("no Suggestion Found "));
    }

    @Override
    public List<OrdersBriefProjection> listOrders(Long expertId) {
        List<OrdersBriefProjection> ordersBriefProjections = suggestionGateway.listOrders(expertId);
        if (ordersBriefProjections.isEmpty())
            throw new EntityNotFoundException("no list Found for this expert");
        return ordersBriefProjections;
    }

    @Override
    @Transactional
    public void registerSuggestion(RegisterSuggestionDto suggestionDto) {

        Expert expert = expertOperation.findById(suggestionDto.expertId());
        Orders order = orderOperation.findById(suggestionDto.orderId());
        if (!(order.getOrderStatus().equals(OrderStatus.WaitingForSuggestionOfExperts)
              || order.getOrderStatus().equals(OrderStatus.WaitingForExpertSelection)))
            throw new IllegalStateException("""
                    can not register suggestion
                    because order state must be WaitingForExpertSelection
                    or WaitingForExpertSelection""");
        validateInput(suggestionDto, expert, order);
        Suggestion suggestion = Mapper.ConvertDtoToEntity
                .convertSuggestionDtoToEntity(suggestionDto, expert, order);

        suggestionGateway.save(suggestion);
        if (order.getOrderStatus() != OrderStatus.WaitingForExpertSelection)
            orderOperation.changeOrderStatus(order, OrderStatus.WaitingForExpertSelection);

    }

    private void validateInput(RegisterSuggestionDto suggestionDto, Expert expert, Orders order) {
        Set<String> errors = new HashSet<>();
        double basePrice = order.getSubService().getBasePrice();
        if (basePrice > suggestionDto.priceSuggestion())
            errors.add("your suggested price is less than the Base Price of this SubService");
        if (suggestionGateway.existsSuggestionByExpertAndOrder(expert, order))
            errors.add("the suggestion of this order for this expert are exists");

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    @Override
    public List<SuggestionBriefProjection> listOrderSuggestions(OrderOfCustomerDto orderOfCustomerDto) {

        List<SuggestionBriefProjection> suggestionBriefProjections = suggestionGateway
                .listOrderSuggestions(orderOfCustomerDto.customerId()
                        , orderOfCustomerDto.orderId());
        if (suggestionBriefProjections.isEmpty())
            throw new EntityNotFoundException("no List Found for this customer and this order");
        return suggestionBriefProjections;
    }

    @Override
    public void selectSuggestionOfOrder(Long suggestionId) {
        if (suggestionId == null)
            throw new IllegalArgumentException("suggestionId can not be Null");
        Suggestion suggestion = suggestionGateway.findById(suggestionId)
                .orElseThrow(() -> new EntityNotFoundException("suggestion not found"));
        Orders order = suggestion.getOrder();
        orderOperation.addExpertToOrder(order, suggestion.getExpert(),
                OrderStatus.WaitingForExpertToComeToYourPlace);
    }

}
