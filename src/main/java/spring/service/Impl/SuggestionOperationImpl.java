package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.dto.projection.SuggestionInfoProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;
import spring.entity.Suggestion;
import spring.enumaration.OrderStatus;
import spring.enumaration.Status;
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
        if (!expert.getStatus().equals(Status.CONFIRMED))
            throw new IllegalStateException("you can not register Suggestion. because your" +
                                            " status is not CONFIRMED");
        Orders order = orderOperation.findById(suggestionDto.orderId());
        if (!(order.getOrderStatus().equals(OrderStatus.WaitingForSuggestionOfExperts)
              || order.getOrderStatus().equals(OrderStatus.WaitingForExpertSelection)))
            throw new IllegalStateException("can not register suggestion because order " +
                                            "state must be WaitingForExpertSelection " +
                                            " or WaitingForExpertSelection");
        boolean hasSubService = false;
        Set<SubService> subServices = expert.getSubServices();
        for (SubService subService : subServices) {
            if (subService.equals(order.getSubService())) {
                hasSubService = true;
                break;
            }
        }
        if (!hasSubService)
            throw new IllegalStateException("for this Order,you not permission to register Suggestion" +
                                            "because the relevant subServices has not " +
                                            "been added to your subServices yet");
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
    @Transactional
    public void selectSuggestionOfOrder(Long suggestionId) {
        if (suggestionId == null)
            throw new IllegalArgumentException("suggestionId can not be Null");
        Suggestion suggestion = suggestionGateway.findById(suggestionId)
                .orElseThrow(() -> new EntityNotFoundException("suggestion not found"));
        Orders order = suggestion.getOrder();
        orderOperation.addExpertToOrder(order, suggestion.getExpert(),
                OrderStatus.WaitingForExpertToComeToYourPlace);
    }

    @Override
    public SuggestionInfoProjection getSuggestionInfo(Expert expert, Orders order) {
        return suggestionGateway.getSuggestedTimeStartService(expert, order);
    }

}
