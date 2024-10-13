package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.OrderOfCustomerDto;
import spring.dto.OrdersBriefDto;
import spring.dto.RegisterSuggestionDto;
import spring.dto.SuggestionBriefDto;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.mapper.OrderMapper;
import spring.mapper.SuggestionMapper;
import spring.service.SuggestionOperation;

import java.util.List;

@RestController
@RequestMapping("/v1/suggestions")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionOperation suggestionOperation;
    private final SuggestionMapper suggestionMapper;
    private final OrderMapper orderMapper;

    @GetMapping("/listOrders")
    public ResponseEntity<List<OrdersBriefDto>> listOrders(Long expertId) {
        List<OrdersBriefProjection> ordersBriefProjectionList = suggestionOperation
                .listOrders(expertId);
        List<OrdersBriefDto> ordersBriefDtos = orderMapper.convertEntityToDTO(ordersBriefProjectionList);
        return new ResponseEntity<>(ordersBriefDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerSuggestion(@RequestParam @Valid RegisterSuggestionDto suggestionDto) {
        suggestionOperation.registerSuggestion(suggestionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/listOrderSuggestions")
    public ResponseEntity<List<SuggestionBriefDto>> listOrderSuggestions(OrderOfCustomerDto OrderOfCustomerDto) {
        List<SuggestionBriefProjection> suggestionBriefProjections = suggestionOperation
                .listOrderSuggestions(OrderOfCustomerDto);
        List<SuggestionBriefDto> suggestionBriefDtos = suggestionMapper.convertEntityToDTO(suggestionBriefProjections);

        return new ResponseEntity<>(suggestionBriefDtos, HttpStatus.OK);
    }

}
