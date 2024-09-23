package repository.Impl;

import entity.Work;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.WorkRepository;


public class WorkRepositoryImpl extends BaseEntityRepositoryImpl<Work> implements WorkRepository {
    public WorkRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Work> getEntityClass() {
        return Work.class;
    }

    @Override
    public boolean existsByName(String workName) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                select  1 from Work w where w.name = ?1
                                """, Integer.class);
        query.setParameter(1, workName);
        return !query.getResultList().isEmpty();

    }
}
