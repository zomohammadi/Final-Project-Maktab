package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record OrdersBriefDto(
        @NotNull(message = "serviceId cannot be Null")
        @Min(value = 1, message = "serviceId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "serviceId must be less than or equal to {value}")
        Long orderId,

        @NotBlank(message = "SubService Name cannot be Blank")
        @Size(min = 3, max = 30, message = "SubService Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "SubService Name can only contain letters")
        String subServiceName,

        @NotBlank(message = "address cannot be Blank")
        @Size(min = 10, max = 300, message = "address  must be less than {max} characters" +
                                             "and greater Than {min} characters")
        String address,

        @NotBlank(message = "UserName cannot be Blank")
        @Size(min = 4, max = 20, message = "UserName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String userName,

        @NotNull(message = "priceSuggested cannot be Null")
        @Min(value = 1, message = "priceSuggested must be greater than or equal to {value}")
        @DecimalMax(value = "" + Double.MAX_VALUE, message = "priceSuggested must be less than or equal to {value}")
        Double priceSuggested,

        @Size(max = 300, message = "serviceDescription  must be less Than {max} characters")
        String serviceDescription,

        @NotNull(message = "timeForServiceDone cannot be Null")
        @Future(message = "timeForServiceDone must be in the future")
        ZonedDateTime timeForServiceDone
) {
}
