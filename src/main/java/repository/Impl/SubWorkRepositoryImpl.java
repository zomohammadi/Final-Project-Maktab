package repository.Impl;

import entity.SubWork;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.SubWorkRepository;

public class SubWorkRepositoryImpl extends BaseEntityRepositoryImpl<SubWork> implements SubWorkRepository {
    public SubWorkRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<SubWork> getEntityClass() {
        return SubWork.class;
    }

    @Override
    public boolean existsByName(String subWorkName) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                select  1 from SubWork w where w.name = ?1
                                """, Integer.class);
        query.setParameter(1, subWorkName);
        return !query.getResultList().isEmpty();

    }
}
