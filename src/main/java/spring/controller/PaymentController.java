package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Invoice;
import spring.service.PaymentOperation;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentOperation paymentOperation;

    @PostMapping("/prePayment/{orderId}")
    public ResponseEntity<Invoice> prePayment(@PathVariable Long orderId) {
        Invoice invoice = paymentOperation.prePaymentOperation(orderId);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }
}
