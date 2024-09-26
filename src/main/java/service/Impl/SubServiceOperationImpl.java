package service.Impl;

import dto.ChangeSubServiceDto;
import dto.RegisterSubServiceDto;
import dto.ResponceSubServiceDto;
import entity.Service;
import entity.SubService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.ServiceGateway;
import repository.SubServiceGateway;
import service.SubServiceOperation;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class SubServiceOperationImpl implements SubServiceOperation {
    private final ServiceGateway serviceGateway;
    private final SubServiceGateway subServiceGateway;
    private final Validator validator;

    @Override
    public void subServiceRegister(RegisterSubServiceDto subServiceDto) {
        Set<ConstraintViolation<RegisterSubServiceDto>> violations = validator.validate(subServiceDto);
        boolean exists = subServiceGateway.existsByName(subServiceDto.name());
        Service service = serviceGateway.findById(subServiceDto.serviceId());
        if (!violations.isEmpty() || exists || service == null) {
            for (ConstraintViolation<RegisterSubServiceDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + " Sub Service with this name is already exists" + "\u001B[0m");
            if (service == null)
                System.out.println("\u001B[31m" + " service with this Id not Found" + "\u001B[0m");
            return;
        }
        SubService subService = Mapper.ConvertDtoToEntity.convertSubServiceDtoToEntity(subServiceDto, service);
        subServiceGateway.save(subService);
        System.out.println("SubService Register done");
    }

    @Override
    public List<ResponceSubServiceDto> findAllSubService() {
        List<SubService> subServiceList = subServiceGateway.findAll();
        List<ResponceSubServiceDto> responceSubServiceDtos = subServiceList.stream()
                .map(Mapper.ConvertEntityToDto::convertSubServiceToDto).toList();////Mapper.ConvertEntityToDto.convertSubServiceToDto(subService))
        if (responceSubServiceDtos.isEmpty())
            System.out.println("There are currently no SubService.");
        return responceSubServiceDtos;
    }

    @Override
    public List<ResponceSubServiceDto> findAllSubServiceOfService(Long serviceId) {
        List<SubService> allSubServiceOfService = subServiceGateway.findAllSubServiceOfService(serviceId);
        List<ResponceSubServiceDto> responceSubServiceDtos = allSubServiceOfService.stream()
                .map(Mapper.ConvertEntityToDto::convertSubServiceToDto).toList();
        //.map(s -> Mapper.ConvertEntityToDto.convertSubServiceToDto(s))
        if (responceSubServiceDtos.isEmpty())
            System.out.println("There are currently no SubService.");
        return responceSubServiceDtos;
    }
    @Override
    public void update(ChangeSubServiceDto subServiceDto) {
        if (isNotValid(subServiceDto)) return;
        SubService subService1 = Mapper.ConvertDtoToEntity.convertChangeSubServiceDtoToEntity(subServiceDto);
        SubService subService = subServiceGateway.findById(subService1.getId());
        updateOperation(subServiceDto, subService);

    }

    private void updateOperation(ChangeSubServiceDto subServiceDto, SubService subService) {
        if (subService != null) {
            if (subServiceDto.name() != null) {
                subService.setName(subServiceDto.name());
            }
            if (subServiceDto.description() != null) {
                subService.setDescription(subServiceDto.description());
            }
            if (subServiceDto.BasePrice() != null) {
                subService.setBasePrice(subServiceDto.BasePrice());
            }
            subServiceGateway.update(subService);
            System.out.println("SubService Updated!");
        } else {
            System.out.println("SubService not found");
        }
    }

    private boolean isNotValid(ChangeSubServiceDto subServiceDto) {
        Set<ConstraintViolation<ChangeSubServiceDto>> violations = validator.validate(subServiceDto);
        boolean exists = false;
        if (subServiceDto.name() != null) {
            exists = subServiceGateway.existsByName(subServiceDto.name());
        }
        if (!violations.isEmpty() || exists /*||serviceId==null*/) {
            for (ConstraintViolation<ChangeSubServiceDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + "SubService with this name is already exists" + "\u001B[0m");

            return true;
        }
        return false;
    }

    @Override
    public ResponceSubServiceDto findById(Long subServiceId) {
        SubService subService = subServiceGateway.findById(subServiceId);
        ResponceSubServiceDto responceSubServiceDto = null;
        if (subService != null) {
            responceSubServiceDto = Mapper.ConvertEntityToDto.convertSubServiceToDto(subService);
        }
        return responceSubServiceDto;
    }





}
/* @Override
    public void delete(Long subServiceId) {
        SubService subService = subServiceGateway.findById(subServiceId);
        if (subService != null) {
            subServiceGateway.delete(subService);
            System.out.println("delete Done");
        } else System.err.println("SubService Not Found");
    }*/

   /* public ResponceSubServiceDto findById(Long serviceId) {
        Set<ConstraintViolation<Long>> violations = validator.validate(serviceId);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Long> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return null;
        }

        SubService subService = subServiceGateway.findById(serviceId);
        ResponceSubServiceDto responceSubServiceDto = null;
        if (subService!=null) {
            responceSubServiceDto = Mapper.ConvertEntityToDto.convertSubServiceToDto(subService);
        }
        return responceSubServiceDto;
    }*/