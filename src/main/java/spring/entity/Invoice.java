package spring.entity;

import spring.enumaration.InvoiceStatus;
import spring.enumaration.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice extends BaseEntity {

    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_number_seq")
    @SequenceGenerator(name = "invoice_number_seq", sequenceName = "invoice_number_seq", allocationSize = 1)
    private Long invoiceNumber;

    private String subServiceName;
    private Double price;//priceSuggested

    private String customerFirstName;
    private String customerLastName;
    private String customerUserName;

    private String expertFirstName;
    private String expertLastName;
    private String expertUserName;

    private InvoiceStatus invoiceStatus;
    private PaymentStatus paymentStatus;


}
