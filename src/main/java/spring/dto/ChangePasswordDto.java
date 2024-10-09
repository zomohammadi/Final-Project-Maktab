package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ChangePasswordDto(

        @NotNull(message = "userId cannot be Null")
        @Min(value = 1, message = "userId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "serviceId must be less than or equal to {value}")
        Long userId,

        @NotBlank(message = "Password cannot be Blank")
        @Size(min = 8, max = 8, message = "Password must be exactly {max} characters long")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both letters and numbers")
        String password

        //add old_password
) {
}
