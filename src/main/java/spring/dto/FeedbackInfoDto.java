package spring.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FeedbackInfoDto(
        @NotNull(message = "orderId cannot be Null")
        @Min(value = 1, message = "orderId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "orderId must be less than or equal to {value}")
        Long orderId,

        @NotNull(message = "score cannot be Null")
        @Min(value = 1, message = "score must be greater than or equal to {value}")
        @Max(value = 5, message = "score must be less than or equal to {value}")
        int score,
        //@NotNull(message = "Comment cannot be null")
        String comment
) {
}
