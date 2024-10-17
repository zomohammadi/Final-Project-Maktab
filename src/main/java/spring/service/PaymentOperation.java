package spring.service;

import org.springframework.transaction.annotation.Transactional;
import spring.entity.Expert;
import spring.entity.Invoice;

import java.time.ZonedDateTime;

public interface PaymentOperation {
    Invoice prePaymentOperation(Long orderId);
    void payment(Long orderId);
}
