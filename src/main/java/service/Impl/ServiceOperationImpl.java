package service.Impl;

import dto.ChangeServiceDto;
import entity.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
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

    @Override
    public void update(ChangeServiceDto changeServiceDto) {
        if (isNotValid(changeServiceDto)) return;
        Service service1 = Mapper.ConvertDtoToEntity.convertChangeServiceDtoToEntity(changeServiceDto);
        Service service = serviceGateway.findById(service1.getId());
        updateOperation(changeServiceDto, service);

    }

    private void updateOperation(ChangeServiceDto changeServiceDto, Service service) {
        if (service != null) {
            if (changeServiceDto.name() != null) {
                service.setName(changeServiceDto.name());
            }
            serviceGateway.update(service);
            System.out.println("Service Updated!");
        } else {
            System.out.println("Service not found");
        }
    }

    private boolean isNotValid(ChangeServiceDto changeServiceDto) {
        Set<ConstraintViolation<ChangeServiceDto>> violations = validator.validate(changeServiceDto);
        boolean exists = false;
        if (changeServiceDto.name() != null) {
            exists = serviceGateway.existsByName(changeServiceDto.name());
        }
        if (!violations.isEmpty() || exists) {
            for (ConstraintViolation<ChangeServiceDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + "Service with this name is already exists" + "\u001B[0m");

            return true;
        }
        return false;
    }

    @Override
    public String findById(Long subServiceId) {
        Service service = serviceGateway.findById(subServiceId);
        String responceService = null;
        if (service != null) {
            responceService = service.getName();
        }
        return responceService;
    }
}
