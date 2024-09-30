package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.entity.Service;


public interface ServiceGateway extends JpaRepository<Service, Long> {
    @Query(" select CASE WHEN COUNT(w) > 0 THEN true ELSE false end from Service w where w.name = :serviceName")
    boolean existsByName(String serviceName);
}
