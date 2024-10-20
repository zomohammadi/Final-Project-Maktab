package spring.dto;

import lombok.Builder;
import spring.enumaration.Role;

@Builder
public record UserSearchCriteriaDto(
        String firstName,
        String lastName,
        String emailAddress,
        Role role,
        Boolean showHighestExperts,
        Boolean showLowestExperts,
        String subServiceName
) {
}
