package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import spring.enumaration.OrderStatus;

import java.time.ZonedDateTime;

@Builder
public record ResponceOrderDto(
        @NotBlank(message = "SubService Name cannot be Blank")
        @Size(min = 3, max = 30, message = "SubService Name must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "SubService Name can only contain letters")
        String subServiceName,

        @NotBlank(message = "customerUserName cannot be Blank")
        @Size(min = 4, max = 20, message = "customerUserName must be less than {max} characters" +
                                           "and greater Than {min} characters")

        String customerUserName,

        // @NotBlank(message = "expertUserName cannot be Blank")
        @Size(min = 4, max = 20, message = "expertUserName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String expertUserName,

        @NotNull(message = "priceSuggested cannot be Null")
        @Min(value = 1, message = "priceSuggested must be greater than or equal to {value}")
        @DecimalMax(value = "" + Double.MAX_VALUE, message = "priceSuggested must be less than or equal to {value}")
        Double priceSuggested,

        @NotBlank(message = "address cannot be Blank")
        @Size(min = 10, max = 300, message = "address  must be less than {max} characters" +
                                             "and greater Than {min} characters")
        String address,

        @NotNull(message = "orderStatus cannot be Null")
        OrderStatus orderStatus,

        @NotNull(message = "timeForServiceDone cannot be Null")
        //@FutureOrPresent(message = "timeForServiceDone must be in the present or future")
        @Future(message = "timeForServiceDone must be in the future")
        ZonedDateTime timeForServiceDone,

        @Size(max = 300, message = "serviceDescription  must be less Than {max} characters")
        String serviceDescription
        /*,
        //  @NotNull(message = "invoiceId cannot be Null")
        @Min(value = 1, message = "invoiceId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "invoiceId must be less than or equal to {value}")
        Long invoiceNumber*/
) {
}
