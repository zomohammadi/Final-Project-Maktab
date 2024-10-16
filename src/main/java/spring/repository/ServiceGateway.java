package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Service;


public interface ServiceGateway extends JpaRepository<Service, Long> {

    boolean existsByName(String serviceName);

}
// @Query(" select CASE WHEN COUNT(w) > 0 THEN true ELSE false end from Service w where w.name = :serviceName")
// @Query(" select true  from Service w where w.name = :serviceName")
// @Query(" select count(w)>0  from Service w where w.name = :serviceName")
//  boolean existsByName(String serviceName);