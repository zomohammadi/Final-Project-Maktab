package repository.Impl;

import entity.SubService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.SubServiceGateway;

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
}
