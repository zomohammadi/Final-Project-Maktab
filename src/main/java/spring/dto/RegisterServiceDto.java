package spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterServiceDto(
        @NotBlank(message = "Service Name cannot be Blank")
        @Size(min = 3, max = 30, message = "Service Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Service Name can only contain letters")
        String name
) {
}
