package spring.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.projection.SuggestionInfoProjection;
import spring.entity.Expert;
import spring.entity.Invoice;
import spring.entity.Orders;
import spring.enumaration.OrderStatus;
import spring.repository.ExpertGateway;
import spring.repository.OrderGateway;
import spring.service.InvoiceOperation;
import spring.service.PaymentOperation;
import spring.service.SuggestionOperation;

import java.time.Duration;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentOperationImpl implements PaymentOperation {
    private final OrderGateway orderGateway;

    private final SuggestionOperation suggestionOperation;
    private final ExpertGateway expertGateway;
    private final InvoiceOperation invoiceOperation;

    @Override
    @Transactional
    public Invoice prePaymentOperation(Long orderId) {
        if (orderId == null)
            throw new IllegalArgumentException("orderId can not be Null");
        Orders order = orderGateway.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order not Found"));
        if (!order.getOrderStatus().equals(OrderStatus.Started))
            throw new IllegalStateException("your status is not Started");
        order.setOrderStatus(OrderStatus.Done);
        order.setTimeServiceCompleted(ZonedDateTime.now());
        Expert expert = order.getExpert();
        SuggestionInfoProjection projection = suggestionOperation.getSuggestionInfo(expert, order);
        System.out.println("Projection: " + projection);

        if (projection != null) {
            System.out.println("Suggested Time Start Service: " + projection.getSuggestedTimeStartService());
            System.out.println("Price Suggested: " + projection.getPriceSuggested());
            System.out.println("Duration of Service: " + projection.getDurationOfService());
        }
        ZonedDateTime timeServiceCompleted = order.getTimeServiceCompleted();
        ZonedDateTime suggestedTimeStartService = projection.getSuggestedTimeStartService();
        Double priceSuggested = projection.getPriceSuggested();
        int durationOfService = projection.getDurationOfService();
        setPerformanceScore(timeServiceCompleted, expert, durationOfService, suggestedTimeStartService /*projection*/);
        Invoice invoice = invoiceOperation.createInvoice(order, priceSuggested);
        order.setInvoice(invoice);
        orderGateway.save(order);
        return invoice;


    }

    private void setPerformanceScore(ZonedDateTime actualCompletionTime, Expert expert,
                                     int durationOfService,
                                     ZonedDateTime suggestedTimeStartService
            ) {
        ZonedDateTime expertOfferTime = calculateServiceEndTime(/*projection*/durationOfService,
                suggestedTimeStartService);
        // Check if there's a delay
        if (actualCompletionTime.isAfter(expertOfferTime)) {
            int performanceScore = calculatePerformanceScore(expertOfferTime, actualCompletionTime);
            expert.setPerformanceScore(performanceScore);
            expertGateway.save(expert);
        }
    }

    private ZonedDateTime calculateServiceEndTime(int durationOfService,
                                                  ZonedDateTime suggestedTimeStartService) {
        return suggestedTimeStartService.plusHours(durationOfService);
    }

    private int calculatePerformanceScore(ZonedDateTime expertOfferTime, ZonedDateTime actualCompletionTime) {
        Duration duration = Duration.between(expertOfferTime, actualCompletionTime);
        long hoursDifference = duration.toHours();
        return (int) hoursDifference;
    }

}
