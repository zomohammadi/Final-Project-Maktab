package spring.service.Impl;

import org.springframework.transaction.annotation.Transactional;
import spring.dto.ChangeSubServiceDto;
import spring.dto.RegisterSubServiceDto;
import spring.dto.ResponceSubServiceDto;
import spring.entity.Service;
import spring.entity.SubService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import spring.mapper.Mapper;
import spring.repository.ServiceGateway;
import spring.repository.SubServiceGateway;
import spring.service.SubServiceOperation;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class SubServiceOperationImpl implements SubServiceOperation {
    private final ServiceGateway serviceGateway;
    private final SubServiceGateway subServiceGateway;
    private final Validator validator;

    @Override
    @Transactional
    public void subServiceRegister(RegisterSubServiceDto subServiceDto) {
        Set<ConstraintViolation<RegisterSubServiceDto>> violations = validator.validate(subServiceDto);
        boolean exists = subServiceGateway.existsByName(subServiceDto.name());
        Service service = serviceGateway.findById(subServiceDto.serviceId()).orElse(null);
        if (!violations.isEmpty() || exists || service == null) {
            for (ConstraintViolation<RegisterSubServiceDto> violation : violations) {
                System.out.println("\u001B[3m" + violation.getMessage() + "\u001B[0m");
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
    @Transactional
    public void update(ChangeSubServiceDto subServiceDto) {
        if (isNotValid(subServiceDto)) return;
        SubService subService1 = Mapper.ConvertDtoToEntity.convertChangeSubServiceDtoToEntity(subServiceDto);//  edit
        SubService subService = subServiceGateway.findById(subService1.getId()).orElse(null);
        updateOperation(subServiceDto, subService);

    }

    private void updateOperation(ChangeSubServiceDto subServiceDto, SubService subService) {
        if (subService != null) {
            if (subServiceDto.name() != null) {
                subService.setName(subServiceDto.name()); //method:(subServiceDto.name(),subService.setName())
            }
            if (subServiceDto.description() != null) {//duplicate code
                subService.setDescription(subServiceDto.description());
            }
            if (subServiceDto.BasePrice() != null) {
                subService.setBasePrice(subServiceDto.BasePrice());
            }
            subServiceGateway.save(subService);
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
        SubService subService = subServiceGateway.findById(subServiceId).orElse(null);
        ResponceSubServiceDto responceSubServiceDto = null;
        if (subService != null) {
            responceSubServiceDto = Mapper.ConvertEntityToDto.convertSubServiceToDto(subService);
        }
        return responceSubServiceDto;
    }

}
