package repository.Impl;

import entity.Expert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.ExpertRepository;

import java.util.List;

public class ExpertRepositoryImpl extends UserRepositoryImpl<Expert> implements ExpertRepository {
    public ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }


    public List<byte[]> getPictureByUserName(String userName) {
        TypedQuery<byte[]> query = getEntityManager().createQuery("""
                select e.picture as Picture from Expert e where e.userName = ?1
                                """, byte[].class);
        query.setParameter(1, userName);
        return  query.getResultList();
    }
}
