package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.entity.Credit;

public interface CreditGateway  extends JpaRepository<Credit, Long> {
@Query("""
select c from Users u inner join Credit c on u.credit=c where u.id = :id
""")
    Credit findCreditByCustomerID(@Param("id") Long customerId);
}
