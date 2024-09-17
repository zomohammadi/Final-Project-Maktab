package repository.Impl;

import entity.Expert;
import jakarta.persistence.EntityManager;
import repository.ExpertRepository;

public class ExpertRepositoryImpl extends BaseEntityRepositoryImpl<Expert> implements ExpertRepository {
    public ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }
}
