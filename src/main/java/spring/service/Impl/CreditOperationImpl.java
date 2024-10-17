package spring.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.Credit;
import spring.entity.Invoice;
import spring.repository.CreditGateway;
import spring.service.CreditOperation;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditOperationImpl implements CreditOperation {
    private final CreditGateway creditGateway;

    @Override
    public void withdrawalFromCredit(Long customerId, Invoice invoice) {
        Credit credit = findCreditByCustomerID(customerId);
        double cost = credit.getCreditAmount() - invoice.getPrice();
        if (cost < 0)
            throw new RuntimeException("your credit balance is not enough ");
        credit.setCreditAmount(cost);
    }

    private Credit findCreditByCustomerID(Long customerId) {
        return creditGateway.findCreditByCustomerID(customerId);
    }
}
