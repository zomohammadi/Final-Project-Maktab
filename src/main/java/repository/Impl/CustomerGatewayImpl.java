package repository.Impl;

import entity.Customer;
import jakarta.persistence.EntityManager;
import repository.CustomerGateway;

public class CustomerGatewayImpl extends UserGatewayImpl<Customer > implements CustomerGateway {
    public CustomerGatewayImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

}
