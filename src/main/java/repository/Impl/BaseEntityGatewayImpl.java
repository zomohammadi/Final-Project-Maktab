package repository.Impl;

import entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import repository.BaseEntityGateway;

import java.util.List;

public abstract class BaseEntityGatewayImpl<T extends BaseEntity> implements BaseEntityGateway<T> {
    private final EntityManager entityManager;

    public BaseEntityGatewayImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(T t) {
        if (t != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
        }
    }
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(getEntityClass());
        query.from(getEntityClass());
        return entityManager.createQuery(query).getResultList();
    }


    public T findById(Long id) {
        return entityManager.find(getEntityClass(), id);
    }
    public void update(T t) {
        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract Class<T> getEntityClass();

}
  //  public void delete(T t) {
//        entityManager.getTransaction().begin();
//        entityManager.remove(t);
//        entityManager.getTransaction().commit();
//    }