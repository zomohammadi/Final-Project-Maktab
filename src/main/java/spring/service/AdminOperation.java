package spring.service;

import spring.dto.UserSearchCriteriaDto;
import spring.dto.UserSearchResultDto;

import java.util.List;

public interface AdminOperation {
    void addSubServiceToExpert(Long expertId, Long subServiceId);
    void deleteSubServiceFromExpert(Long expertId, Long subServiceId);
    void confirmedExpert(Long expertId);

    List<UserSearchResultDto> searchUsers(UserSearchCriteriaDto searchCriteria);
}
