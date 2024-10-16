package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.RegisterOrderDto;
import spring.dto.ResponceOrderDto;
import spring.entity.Orders;
import spring.mapper.Mapper;
import spring.service.OrderOperation;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderOperation orderOperation;

    @PostMapping
    public ResponseEntity<Void> orderRegister(@RequestBody @Valid RegisterOrderDto orderDto) {
        orderOperation.orderRegister(orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceOrderDto> findById(@PathVariable("id") Long orderId) {
        Orders order = orderOperation.findById(orderId);
        ResponceOrderDto responceOrderDto = Mapper.ConvertEntityToDto.convertOrderToDto(order);
        return new ResponseEntity<>(responceOrderDto, HttpStatus.OK);
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<Void> changeOrderStatusToStarted(@PathVariable("id") Long orderId) {
        orderOperation.changeOrderStatusToStarted(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
  /*  @PutMapping("/orders/{id}/complete")
    public ResponseEntity<Void> changeOrderStatusToDone(@PathVariable("id") Long orderId) {
        orderOperation.changeOrderStatusToDone(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
