package repository.Impl;

import entity.Customer;
import jakarta.persistence.EntityManager;
import repository.CustomerRepository;

public class CustomerRepositoryImpl extends BaseEntityRepositoryImpl<Customer > implements CustomerRepository {
    public CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer > getEntityClass() {
        return Customer.class;
    }

}
