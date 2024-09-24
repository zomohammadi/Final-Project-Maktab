package repository.Impl;

import entity.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.ServiceGateway;


public class ServiceGatewayImpl extends BaseEntityGatewayImpl<Service> implements ServiceGateway {
    public ServiceGatewayImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Service> getEntityClass() {
        return Service.class;
    }

    @Override
    public boolean existsByName(String serviceName) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                select  1 from Service w where w.name = ?1
                                """, Integer.class);
        query.setParameter(1, serviceName);
        return !query.getResultList().isEmpty();

    }
}
