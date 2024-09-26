package repository.Impl;

import entity.SubService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.SubServiceGateway;

import java.util.List;

public class SubServiceGatewayImpl extends BaseEntityGatewayImpl<SubService> implements SubServiceGateway {
    public SubServiceGatewayImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<SubService> getEntityClass() {
        return SubService.class;
    }

    @Override
    public boolean existsByName(String subServiceName) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                select  1 from SubService w where w.name = ?1
                                """, Integer.class);
        query.setParameter(1, subServiceName);
        return !query.getResultList().isEmpty();

    }

    @Override
    public List<SubService> findAllSubServiceOfService(Long serviceId) {
        TypedQuery<SubService> query = getEntityManager().createQuery("""
                select s from SubService s inner join Service e on s.service=e where e.id = ?1
                                """, SubService.class);
        query.setParameter(1, serviceId);
        return query.getResultList();
    }
}
