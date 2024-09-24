package service;

import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;

import java.util.List;

public interface AdminService {
    void addSubWorkToExpert(Long expertId, Long subWorkId);
    void deleteSubWorkFromExpert(Long expertId, Long subWorkId);

}
