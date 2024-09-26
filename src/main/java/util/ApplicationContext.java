package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import repository.*;
import repository.Impl.*;
import service.*;
import service.Impl.*;

public class ApplicationContext {

    private EntityManagerFactory enf;
    private EntityManager em;
    ValidatorFactory validatorFactory;
    private Validator validator;
    //service
    private final ExpertOperation expertOperation;
    private final CustomerOperation customerOperation;
    private final AdminOperation adminOperation;
    private final ServiceOperation serviceOperation;
    private final SubServiceOperation subServiceOperation;
    private final OrderOperation orderOperation;

    public ApplicationContext() {
        this.em = getEntityManager();
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }
        //repository
        ExpertGateway expertGateway = new ExpertGatewayImpl(em);
        CustomerGateway customerGateway = new CustomerGatewayImpl(em);
        ServiceGateway serviceGateway = new ServiceGatewayImpl(em);
        SubServiceGateway subServiceGateway = new SubServiceGatewayImpl(em);
        OrderGateway orderGateway = new OrderGatewayImpl(em);

        //service
        expertOperation = new ExpertOperationImp(expertGateway, getValidator());
        customerOperation = new CustomerOperationImpl(customerGateway, getValidator());
        adminOperation = new AdminOperationImpl(subServiceGateway, expertGateway);
        serviceOperation = new ServiceOperationImpl(serviceGateway, getValidator());
        subServiceOperation = new SubServiceOperationImpl(serviceGateway, subServiceGateway, getValidator());
        orderOperation = new OrderOperationImpl(orderGateway, subServiceGateway, customerGateway, getValidator());
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        if (enf == null) {
            enf = Persistence.createEntityManagerFactory("default");
        }
        return enf;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            em = getEntityManagerFactory().createEntityManager();
        }
        return em;
    }


    public Validator getValidator() {
        if (validator == null) {
            validator = validatorFactory.getValidator();
        }
        return validator;
    }
    //getter

    public ExpertOperation getExpertOperation() {
        return expertOperation;
    }

    public CustomerOperation getCustomerOperation() {
        return customerOperation;
    }

    public AdminOperation getAdminOperation() {
        return adminOperation;
    }

    public ServiceOperation getServiceOperation() {
        return serviceOperation;
    }

    public SubServiceOperation getSubServiceOperation() {
        return subServiceOperation;
    }

    public OrderOperation getOrderOperation() {
        return orderOperation;
    }
}

    /*public ValidatorFactory getValidatorFactory() {
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }
        return validatorFactory;
    }*/