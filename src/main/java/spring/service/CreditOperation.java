package spring.service;

import spring.entity.Invoice;

public interface CreditOperation {
    void withdrawalFromCredit(Long customerId, Invoice invoice);
}
