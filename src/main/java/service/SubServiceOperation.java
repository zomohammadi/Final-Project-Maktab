package service;

import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;

import java.util.List;

public interface SubServiceOperation {
    void subWorkRegister(RegisterSubWorkDto subWorkDto);
    List<ResponceSubWorkDto> findAllSubWork();
}
