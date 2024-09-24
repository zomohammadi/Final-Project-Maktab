package dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterSubServiceDto(

        @NotBlank(message = "SubService Name cannot be Blank")
        @Size(min = 3, max = 30, message = "SubService Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "SubService Name can only contain letters")
        String name,

        @NotBlank(message = "description Name cannot be Blank")
        @Size(min = 3, max = 30, message = "description Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String description,

        @NotNull(message = "Base Price cannot be Null")
        @Min(value = 100000, message = "Base Price must be greater Than {value} numbers ")
        @Max(value = 900000000, message = "Base Price must be less Than {value} numbers ")
        //@Positive
        Double BasePrice,

        @NotNull(message = "serviceId cannot be Null")
        Long serviceId
) {
}
