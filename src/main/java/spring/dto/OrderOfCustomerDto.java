package spring.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderOfCustomerDto(
        @NotNull(message = "customerId cannot be Null")
        @Min(value = 1, message = "customerId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "customerId must be less than or equal to {value}")
        Long customerId,

        @NotNull(message = "orderId cannot be Null")
        @Min(value = 1, message = "customerId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "customerId must be less than or equal to {value}")
        Long orderId
) {
}
