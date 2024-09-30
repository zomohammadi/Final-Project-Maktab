package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ChangeServiceDto(
        @NotBlank(message = "Service Name cannot be Blank")
        @Size(min = 3, max = 30, message = "Service Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Service Name can only contain letters")
        String name,

        @NotNull(message = "ServiceId cannot be Null")
        @Min(value = 1, message = "ServiceId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "ServiceId must be less than or equal to {value}")
        Long ServiceId
) {

}
