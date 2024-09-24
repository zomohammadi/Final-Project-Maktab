package repository;

import entity.Service;

public interface ServiceGateway extends BaseEntityGateway<Service> {
    boolean existsByName(String serviceName);
}
