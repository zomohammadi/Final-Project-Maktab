package service.Impl;

import entity.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import repository.ServiceGateway;
import service.ServiceOperation;

import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
public class ServiceOperationImpl implements ServiceOperation {
    private final ServiceGateway serviceGateway;
    private final Validator validator;
    @Override
    public void serviceRegister(String serviceName) {
        Service service = Service.builder().name(serviceName).build();
        Set<ConstraintViolation<Service>> violations = validator.validate(service);
        boolean exists = serviceGateway.existsByName(serviceName);
        if (!violations.isEmpty() || exists) {
            for (ConstraintViolation<Service> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + "service with this name is already exists" + "\u001B[0m");
            return;
        }
       /* if (serviceGateway.existsByName(serviceName))
            throw new FoundException("service with this name is already exists");*/

        serviceGateway.save(service);
        System.out.println("service Register done");


    }
    @Override
    public List<String> findAllService() {
        List<String> list = serviceGateway.findAll().stream().map(Service::getName).toList();
        if (list.isEmpty())
            System.out.println("There are currently no Service.");
        return list;
    }
}
