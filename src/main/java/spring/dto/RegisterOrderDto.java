package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record RegisterOrderDto(

        @NotNull(message = "customerId cannot be Null")
        @Min(value = 1, message = "customerId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "customerId must be less than or equal to {value}")
        Long customerId,

        @NotNull(message = "serviceId cannot be Null")
        @Min(value = 1, message = "serviceId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "serviceId must be less than or equal to {value}")
        Long subServiceId,

        @NotNull(message = "priceSuggested cannot be Null")
        @Min(value = 1, message = "priceSuggested must be greater than or equal to {value}")
        @DecimalMax(value = "" + Double.MAX_VALUE, message = "priceSuggested must be less than or equal to {value}")
        Double priceSuggested,

        @NotBlank(message = "address cannot be Blank")
        @Size(min = 10, max = 300, message = "address  must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String address,

        @NotNull(message = "timeForServiceDone cannot be Null")
        //@FutureOrPresent(message = "timeForServiceDone must be in the present or future")
        @Future(message = "timeForServiceDone must be in the future")
        ZonedDateTime timeForServiceDone,

        @Size( max = 300, message = "serviceDescription  must be less Than {max} characters")
        String serviceDescription
) {
}