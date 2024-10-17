package spring.service;

import spring.dto.UserSearchCriteriaDto;
import spring.entity.Users;

import java.util.List;

public interface AdminOperation {
    void addSubServiceToExpert(Long expertId, Long subServiceId);
    void deleteSubServiceFromExpert(Long expertId, Long subServiceId);
    void confirmedExpert(Long expertId);

    List<Users> searchUsers(UserSearchCriteriaDto searchCriteria);
}
