package spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserSearchCriteriaDto (
    @Size(max = 50) String role,
    @Size(max = 50) String firstName,
    @Size(max = 50) String lastName,
    @Email String emailAddress,
    String subService,
    Double minScore,
    Double maxScore
){}
