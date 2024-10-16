package spring.service;

import spring.entity.Invoice;

public interface PaymentOperation {
    Invoice prePaymentOperation(Long orderId);
}
