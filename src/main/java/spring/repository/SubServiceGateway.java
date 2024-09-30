package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import spring.entity.SubService;

import java.util.List;

public interface SubServiceGateway extends JpaRepository<SubService, Long> {
    @Query(" select CASE WHEN COUNT(w) > 0 THEN true ELSE false end from SubService w where w.name = :subServiceName")
    boolean existsByName(String subServiceName);

    @Query("select s from SubService s inner join Service e on s.service=e where e.id = :serviceId")
    List<SubService> findAllSubServiceOfService(Long serviceId);
}
