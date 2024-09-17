package dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record RegisterExpertDto(
        @NotBlank(message = "FirstName cannot be Blank")
        @Size(max = 30, message = "FirstName must be less than {max} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "FirstName can only contain letters")
        String firstName,
        @NotBlank(message = "LastName cannot be Blank")
        @Size(max = 40, message = "LastName must be less than {max} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "LastName can only contain letters")
        String lastName,
        @NotBlank(message = "EmailAddress cannot be Blank")
        @Email(message = "EmailAddress must be a valid email address")
        String emailAddress,

        @NotBlank(message = "UserName cannot be Blank")
        @Size(max = 20, message = "UserName must be less than {max} characters")
        String userName,

        @NotBlank(message = "Password cannot be Blank")
        @Size(min = 8, max = 8, message = "Password must be exactly {max} characters long")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both letters and numbers")
        String password,

        @Min(value = 0, message = "the score not be less than {value}")
        @Max(value = 5, message = "the score not be greater than {value}")
        int score

      /*  @NotBlank(message = "Picture cannot be Blank")
        byte[] picture*/


) {
}
