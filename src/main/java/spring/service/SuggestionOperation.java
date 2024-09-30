package spring.service;

import spring.dto.RegisterSuggestionDto;
import spring.dto.projection.OrdersBriefProjection;

import java.util.List;

public interface SuggestionOperation {
    List<OrdersBriefProjection> listOrders(Long expertId);
    void registerSuggestion(RegisterSuggestionDto suggestionDto);
}
