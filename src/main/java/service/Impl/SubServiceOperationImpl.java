package service.Impl;

import dto.RegisterSubServiceDto;
import dto.ResponceSubServiceDto;
import entity.SubService;
import entity.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.SubServiceGateway;
import repository.ServiceGateway;
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
}
