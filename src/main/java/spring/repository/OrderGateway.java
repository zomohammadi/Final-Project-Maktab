package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.SubService;

public interface OrderGateway extends JpaRepository<Orders, Long> {

     int countByExpertAndSubService(Expert expert, SubService subService);
}
  /*  @Query("""
select count(o.expert) from Orders o where o.expert = :expert and o.subService = : subService
""")*/