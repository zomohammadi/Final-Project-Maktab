package spring.service.Impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ChangeServiceDto;
import spring.dto.RegisterServiceDto;
import spring.entity.Service;
import spring.exception.NotFoundException;
import spring.repository.ServiceGateway;
import spring.service.ServiceOperation;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@org.springframework.stereotype.Service
public class ServiceOperationImpl implements ServiceOperation {
    private final ServiceGateway serviceGateway;

    @Override
    @Transactional
    public void serviceRegister(RegisterServiceDto serviceDto) {
        if (serviceGateway.existsByName(serviceDto.name())) {
            throw new EntityExistsException("service with this name is already exists");
        }
        Service service = Service.builder().name(serviceDto.name()).build();
        serviceGateway.save(service);
    }

    @Override
    public List<String> findAllService() {
        List<String> list = serviceGateway.findAll().stream().map(Service::getName).toList();
        if (list.isEmpty())
            throw new NoSuchElementException("There are currently no Service.");
        return list;
    }

    @Override
    @Transactional
    public void update(ChangeServiceDto changeServiceDto) {
        if (serviceGateway.existsByName(changeServiceDto.name()))
            throw new EntityExistsException("Service with this name is already exists");
        Service service = findById(changeServiceDto.ServiceId());
        service.setName(changeServiceDto.name());
        serviceGateway.save(service);
    }

    @Override
    public Service findById(Long serviceId) {
        if (serviceId == null)
            throw new IllegalArgumentException("serviceId can not be Null");
        return serviceGateway.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
    }
}
