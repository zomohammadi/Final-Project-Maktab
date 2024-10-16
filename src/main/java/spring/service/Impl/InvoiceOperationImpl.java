package spring.service.Impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.Invoice;
import spring.entity.Orders;
import spring.enumaration.InvoiceStatus;
import spring.repository.InvoiceGateway;
import spring.service.InvoiceOperation;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InvoiceOperationImpl implements InvoiceOperation {
    private final InvoiceGateway invoiceGateway;
    private final JdbcTemplate jdbcTemplate;

    // This method will run once when the bean is initialized
    @PostConstruct
    public void createInvoiceNumberSequenceIfNotExists() {
        try {
            jdbcTemplate.queryForObject("SELECT nextval('invoice_number_seq')", Long.class);
        } catch (Exception e) {
            // If the sequence doesn't exist, create it
            System.out.println("Sequence 'invoice_number_seq' not found, creating it.");
            jdbcTemplate.execute("CREATE SEQUENCE invoice_number_seq " +
                                 "START WITH 1 " +
                                 "INCREMENT BY 1 " +
                                 "NO MINVALUE " +
                                 "NO MAXVALUE " +
                                 "CACHE 1;");
        }
    }

    @Transactional
    public Long generateInvoiceNumber() {
        // Now, we assume the sequence already exists
        return jdbcTemplate.queryForObject("SELECT nextval('invoice_number_seq')", Long.class);
    }

    @Transactional
    public Invoice createInvoice(Orders order, Double priceSuggested) {
        // Generate the invoice number
        Long invoiceNumber = generateInvoiceNumber();

        // Create and save the invoice
        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)  // Set the invoice number
                .subServiceName(order.getSubService().getName())
                .price(priceSuggested)
                .customerFirstName(order.getCustomer().getFirstName())
                .customerLastName(order.getCustomer().getLastName())
                .customerUserName(order.getCustomer().getUserName())
                .expertFirstName(order.getExpert().getFirstName())
                .expertLastName(order.getExpert().getLastName())
                .expertUserName(order.getExpert().getUserName())
                .invoiceStatus(InvoiceStatus.UnPaid)
                .build();

        return invoiceGateway.save(invoice);  // Save and return the invoice
    }
}
