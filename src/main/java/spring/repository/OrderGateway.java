package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Orders;

public interface OrderGateway extends JpaRepository<Orders, Long> {


}
