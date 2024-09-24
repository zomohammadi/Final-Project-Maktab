package repository;

import entity.SubService;

public interface SubServiceGateway extends BaseEntityGateway<SubService> {
    boolean existsByName(String serviceName);
}
