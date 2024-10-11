package spring.service.Impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ChangeSubServiceDto;
import spring.dto.RegisterSubServiceDto;
import spring.dto.ResponceSubServiceDto;
import spring.entity.Service;
import spring.entity.SubService;
import spring.mapper.Mapper;
import spring.repository.ServiceGateway;
import spring.repository.SubServiceGateway;
import spring.service.SubServiceOperation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class SubServiceOperationImpl implements SubServiceOperation {
    private final ServiceGateway serviceGateway;
    private final SubServiceGateway subServiceGateway;

    @Override
    @Transactional
    public void subServiceRegister(RegisterSubServiceDto subServiceDto) {
        if (subServiceGateway.existsByName(subServiceDto.name()))
            throw new EntityExistsException(" Sub Service with this name is already exists");
        Service service = serviceGateway.findById(subServiceDto.serviceId())
                .orElseThrow(() -> new EntityExistsException(" service not Found"));
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
            throw new NoSuchElementException("There are currently no SubService.");
        return responceSubServiceDtos;
    }

    @Override
    public List<ResponceSubServiceDto> findAllSubServiceOfService(Long serviceId) {
        List<SubService> allSubServiceOfService = subServiceGateway.findAllSubServiceOfService(serviceId);
        List<ResponceSubServiceDto> responceSubServiceDtos = allSubServiceOfService.stream()
                .map(Mapper.ConvertEntityToDto::convertSubServiceToDto).toList();
        //.map(s -> Mapper.ConvertEntityToDto.convertSubServiceToDto(s))
        if (responceSubServiceDtos.isEmpty())
            throw new NoSuchElementException("There are currently no SubService.");
        return responceSubServiceDtos;
    }

    @Override
    @Transactional
    public void update(ChangeSubServiceDto subServiceDto) {
        if (subServiceDto.name() != null) {
            if (subServiceGateway.existsByName(subServiceDto.name()))
                throw new EntityExistsException("SubService with this name is already exists");
        }
        SubService subService = findById(subServiceDto.subServiceId());
        updateOperation(subServiceDto, subService);

    }


    private void updateOperation(ChangeSubServiceDto subServiceDto, SubService subService) {

        //applyIfNotNull(subServiceDto.name(), name ->  subService.setName(name))
        applyIfNotNull(subServiceDto.name(), subService::setName);
        applyIfNotNull(subServiceDto.description(), subService::setDescription);
        applyIfNotNull(subServiceDto.BasePrice(), subService::setBasePrice);
        subServiceGateway.save(subService);

    }

    private <T> void applyIfNotNull(T value, Consumer<T> setterMethod) {
        if (value != null)
            setterMethod.accept(value);
    }


    @Override
    public SubService findById(Long subServiceId) {
        if (subServiceId == null)
            throw new IllegalArgumentException("subServiceId can not be Null");
        return subServiceGateway.findById(subServiceId)
                .orElseThrow(() -> new EntityNotFoundException("subServiceId not found"));
    }

}
 /*   @Override
    public ResponceSubServiceDto findById(Long subServiceId) {
       *//* SubService subService = subServiceGateway.findById(subServiceId).orElse(null);
        ResponceSubServiceDto responceSubServiceDto = null;
        if (subService != null) {
            responceSubServiceDto = Mapper.ConvertEntityToDto.convertSubServiceToDto(subService);
        }
        return responceSubServiceDto;*//* // impl this convert SubService to ResponceSubServiceDto in controller

    }
*/
