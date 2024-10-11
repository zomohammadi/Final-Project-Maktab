package spring.service;

import spring.dto.ChangeSubServiceDto;
import spring.dto.RegisterSubServiceDto;
import spring.dto.ResponceSubServiceDto;
import spring.entity.SubService;

import java.util.List;

public interface SubServiceOperation {
    void subServiceRegister(RegisterSubServiceDto subServiceDto);

    List<ResponceSubServiceDto> findAllSubService();

    List<ResponceSubServiceDto> findAllSubServiceOfService(Long serviceId);

    void update(ChangeSubServiceDto subServiceDto);

    SubService findById(Long subServiceId);

    //void delete(Long subServiceId);
}
