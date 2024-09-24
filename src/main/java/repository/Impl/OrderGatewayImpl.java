package repository.Impl;

import entity.Orders;
import jakarta.persistence.EntityManager;
import repository.OrderGateway;

public class OrderGatewayImpl extends BaseEntityGatewayImpl<Orders> implements OrderGateway {
    public OrderGatewayImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Orders> getEntityClass() {
        return Orders.class;
    }
}
