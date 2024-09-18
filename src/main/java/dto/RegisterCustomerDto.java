package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterCustomerDto(
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
        String password
) {

}