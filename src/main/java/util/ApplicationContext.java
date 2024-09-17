package util;

import entity.Expert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import repository.BaseEntityRepository;
import repository.Impl.ExpertRepositoryImpl;
import service.Impl.ExpertServiceImp;
import service.ExpertService;

public class ApplicationContext {

    private EntityManagerFactory enf;
    private EntityManager em;
     ValidatorFactory validatorFactory;
    private Validator validator;
//service
private final ExpertService expertService;
    public ApplicationContext() {
        this.em = getEntityManager();
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }
        BaseEntityRepository<Expert> expertBaseEntityRepository = new ExpertRepositoryImpl(em);

        //service
         expertService = new ExpertServiceImp(expertBaseEntityRepository, getValidator());

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
}
