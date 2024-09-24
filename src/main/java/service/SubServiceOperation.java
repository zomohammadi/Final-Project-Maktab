package service;

import dto.RegisterSubServiceDto;
import dto.ResponceSubServiceDto;

import java.util.List;

public interface SubServiceOperation {
    void subServiceRegister(RegisterSubServiceDto subServiceDto);
    List<ResponceSubServiceDto> findAllSubService();
}
