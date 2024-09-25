package service;

import dto.ChangeSubServiceDto;
import dto.RegisterSubServiceDto;
import dto.ResponceSubServiceDto;

import java.util.List;

public interface SubServiceOperation {
    void subServiceRegister(RegisterSubServiceDto subServiceDto);
    List<ResponceSubServiceDto> findAllSubService();
    List<ResponceSubServiceDto> findAllSubServiceOfService(Long serviceId);
    void update(ChangeSubServiceDto subServiceDto);
}
