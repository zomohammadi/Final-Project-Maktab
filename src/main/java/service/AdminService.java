package service;

import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;
import entity.SubWork;
import entity.Work;

import java.util.List;

public interface AdminService {
    void workRegister(String workName);
    void subWorkRegister(RegisterSubWorkDto subWorkDto);
    List<String> findAllWork();
    List<ResponceSubWorkDto> findAllSubWork();
    void addSubWorkToExpert(Long expertId, Long subWorkId);
    void deleteSubWorkFromExpert(Long expertId, Long subWorkId);
    void changeExpertStatus(Long expertId, String status);
}
