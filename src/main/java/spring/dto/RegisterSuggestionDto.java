package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record RegisterSuggestionDto(
        @NotNull(message = "expertId cannot be Null")
        @Min(value = 1, message = "expertId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "expertId must be less than or equal to {value}")
        Long expertId,

        @NotNull(message = "orderId cannot be Null")
        @Min(value = 1, message = "orderId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "orderId must be less than or equal to {value}")
        Long orderId,

        @NotNull(message = "Base Price cannot be Null")
        @Min(value = 100000, message = "Base Price must be greater Than {value} numbers ")
        @Max(value = 900000000, message = "Base Price must be less Than {value} numbers ")
        Double priceSuggestion,

        @NotNull(message = "suggestedTimeStartService cannot be Null")
        @Future(message = "suggestedTimeStartService must be in the future")
        ZonedDateTime suggestedTimeStartService,

        @NotBlank(message = "duration Of Service cannot be Blank")
        @Size(min = 3, max = 30, message = "duration Of Service  must be less than {max} characters " +
                                           "and greater Than {min} characters")
        String durationOfService
) {
}
