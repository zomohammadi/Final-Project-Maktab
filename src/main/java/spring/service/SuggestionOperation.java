package spring.service;

import spring.dto.OrderOfCustomerDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.dto.projection.SuggestionInfoProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.Suggestion;

import java.util.List;

public interface SuggestionOperation {
    Suggestion findById(Long suggestionId);

    List<OrdersBriefProjection> listOrders(Long expertId);

    void registerSuggestion(RegisterSuggestionDto suggestionDto);

    List<SuggestionBriefProjection> listOrderSuggestions(OrderOfCustomerDto orderOfCustomerDto);

    void selectSuggestionOfOrder(Long suggestionId);

    SuggestionInfoProjection getSuggestionInfo(Expert expert, Orders order);

}
 /*ZonedDateTime calculateServiceEndTime(Suggestion suggestion);
    int calculatePenalty(Suggestion suggestion);*/