package repository;

import entity.SubService;

import java.util.List;

public interface SubServiceGateway extends BaseEntityGateway<SubService> {
    boolean existsByName(String serviceName);
    List<SubService> findAllSubServiceOfService(Long serviceId);
}
