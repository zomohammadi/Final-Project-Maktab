package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Invoice;

public interface InvoiceGateway  extends JpaRepository<Invoice, Long> {

}
