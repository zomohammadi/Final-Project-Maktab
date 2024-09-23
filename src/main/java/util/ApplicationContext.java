package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import repository.CustomerRepository;
import repository.ExpertRepository;
import repository.Impl.CustomerRepositoryImpl;
import repository.Impl.ExpertRepositoryImpl;
import repository.Impl.SubWorkRepositoryImpl;
import repository.Impl.WorkRepositoryImpl;
import repository.SubWorkRepository;
import repository.WorkRepository;
import service.CustomerService;
import service.Impl.CustomerServiceImpl;
import service.Impl.ExpertServiceImp;
import service.ExpertService;
import service.Impl.AdminServiceImpl;
import service.AdminService;

public class ApplicationContext {

    private EntityManagerFactory enf;
    private EntityManager em;
    ValidatorFactory validatorFactory;
    private Validator validator;
    //service
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final AdminService adminService;

    public ApplicationContext() {
        this.em = getEntityManager();
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }
        //repository
        //BaseEntityRepository<Expert> expertBaseEntityRepository = new ExpertRepositoryImpl(em);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(em);
        CustomerRepository customerRepository = new CustomerRepositoryImpl(em);
        // BaseEntityRepository<Customer> customerBaseEntityRepository = new CustomerRepositoryImpl(em);
        WorkRepository workRepository = new WorkRepositoryImpl(em);
        SubWorkRepository subWorkRepository = new SubWorkRepositoryImpl(em);


        //service
        expertService = new ExpertServiceImp(expertRepository, getValidator());
        customerService = new CustomerServiceImpl(customerRepository, getValidator());
        adminService = new AdminServiceImpl(workRepository, subWorkRepository, getValidator());
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

    /*public ValidatorFactory getValidatorFactory() {
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }
        return validatorFactory;
    }*/

    public Validator getValidator() {
        if (validator == null) {
            validator = validatorFactory.getValidator();
        }
        return validator;
    }
    //getter

    public ExpertService getExpertService() {
        return expertService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
