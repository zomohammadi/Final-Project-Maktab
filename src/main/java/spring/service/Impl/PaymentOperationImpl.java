package spring.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.projection.SuggestionInfoProjection;
import spring.entity.Customer;
import spring.entity.Expert;
import spring.entity.Invoice;
import spring.entity.Orders;
import spring.enumaration.InvoiceStatus;
import spring.enumaration.PaymentStatus;
import spring.repository.ExpertGateway;
import spring.repository.OrderGateway;
import spring.service.*;

import java.time.Duration;
import java.time.ZonedDateTime;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentOperationImpl implements PaymentOperation {
    private final OrderGateway orderGateway;
    private final OrderOperation orderOperation;
    private final SuggestionOperation suggestionOperation;
    private final ExpertGateway expertGateway;
    private final InvoiceOperation invoiceOperation;
    private final CreditOperation creditOperation;

    @Override
    @Transactional
    public Invoice prePaymentOperation(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");

        Orders order = orderOperation.findById(orderId);
        orderOperation.changeOrderStatusToDone(order);

        Expert expert = order.getExpert();
        SuggestionInfoProjection projection = suggestionOperation.getSuggestionInfo(expert, order);

        setPerformanceScore(order.getTimeServiceCompleted(), expert, projection.getDurationOfService()
                , projection.getSuggestedTimeStartService());
        Invoice invoice = invoiceOperation.createInvoice(order, projection.getPriceSuggested());
        order.setInvoice(invoice);
        orderGateway.save(order);
        return invoice;


    }


    public void setPerformanceScore(ZonedDateTime actualCompletionTime, Expert expert,
                                    int durationOfService,
                                    ZonedDateTime suggestedTimeStartService
    ) {
        ZonedDateTime expertOfferTime = calculateServiceEndTime(durationOfService,
                suggestedTimeStartService);
        // Check if there's a delay
        if (actualCompletionTime.isAfter(expertOfferTime)) {
            int performanceScore = calculatePerformanceScore(expertOfferTime, actualCompletionTime);
            expert.setPerformanceScore(performanceScore);

        } else expert.setPerformanceScore(0);
        expertGateway.save(expert);
    }

    private ZonedDateTime calculateServiceEndTime(int durationOfService,
                                                  ZonedDateTime suggestedTimeStartService) {
        return suggestedTimeStartService.plusHours(durationOfService);
    }

    private int calculatePerformanceScore(ZonedDateTime expertOfferTime
            , ZonedDateTime actualCompletionTime) {
        Duration duration = Duration.between(expertOfferTime, actualCompletionTime);
        long hoursDifference = duration.toHours();
        return (int) hoursDifference;
    }

    @Override
    @Transactional
    public void payment(Long orderId) {
        Orders order = orderOperation.findById(orderId);
        Invoice invoice = order.getInvoice();
        Customer customer = order.getCustomer();
        creditOperation.withdrawalFromCredit(customer.getId(), invoice);
        invoice.setPaymentStatus(PaymentStatus.Credit);
        invoice.setInvoiceStatus(InvoiceStatus.Paid);
        orderOperation.changeOrderStatusToPaid(order);
    }
}
