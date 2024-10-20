package spring.dto;

import lombok.Builder;
import spring.enumaration.Role;

@Builder
public record UserSearchResultDto(
        Long id,
        String firstName,
        String lastName,
        String emailAddress,
        Role role,
        String userName,
        Double score
) {
}
