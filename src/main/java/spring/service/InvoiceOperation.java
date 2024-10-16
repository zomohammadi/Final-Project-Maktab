package spring.service;

import spring.entity.Invoice;
import spring.entity.Orders;

public interface InvoiceOperation {
  //  Invoice createInvoice(Orders order, SuggestionInfoProjection projection, Expert expert);
    Invoice createInvoice(Orders order,Double priceSuggested);
}
