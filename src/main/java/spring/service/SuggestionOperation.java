package spring.service;

import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.entity.Suggestion;

import java.util.List;

public interface SuggestionOperation {
    Suggestion findById(Long suggestionId);

    List<OrdersBriefProjection> listOrders(Long expertId);

    void registerSuggestion(RegisterSuggestionDto suggestionDto);

    List<SuggestionBriefProjection> listOrderSuggestions(OrderOfCustomerDto orderOfCustomerDto);
    void selectSuggestionOfOrder(Long suggestionId);
}
