package repository.Impl;

import entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.UserRepository;

public abstract class UserRepositoryImpl<T extends BaseEntity> extends BaseEntityRepositoryImpl<T> implements UserRepository<T> {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public boolean existUserByNationalCode(String nationalCode) {
        TypedQuery<Integer> query = getEntityManager().createQuery("select 1 from Users u inner join " +
                                                                   getEntityClass().getSimpleName() +
                                                                   " d on u.id = d.id " +
                                                                   "where u.nationalCode = ?1"
                , Integer.class);
        query.setParameter(1, nationalCode);
        return query.getResultList().isEmpty();

    }

    public boolean existUserByMobileNumber(String mobileNumber) {
        TypedQuery<Integer> query = getEntityManager().createQuery("select 1 from Users u inner join " +
                                                                   getEntityClass().getSimpleName() +
                                                                   " d on u.id = d.id " +
                                                                   "where u.mobileNumber = ?1"
                , Integer.class);
        query.setParameter(1, mobileNumber);
        return query.getResultList().isEmpty();
    }

    public boolean existUserByEmailAddress(String emailAddress) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                        select 1 from Users where emailAddress = ?1 
                                                              """
                , Integer.class);
        query.setParameter(1, emailAddress);
        return query.getResultList().isEmpty();
    }

    public boolean existUserByUserName(String userName) {
        TypedQuery<Integer> query = getEntityManager().createQuery("""
                        select 1 from Users u where u.userName = ?1 
                                """
                , Integer.class);
        query.setParameter(1, userName);
        return query.getResultList().isEmpty();
    }
}
