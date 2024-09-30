package spring.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ChangeCustomerDto(
        //@NotBlank(message = "FirstName cannot be Blank")
        @Size(min = 3, max = 30, message = "FirstName must be less than {max} characters " +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "FirstName can only contain letters")
        String firstName,

        //@NotBlank(message = "LastName cannot be Blank")
        @Size(min = 3, max = 40, message = "LastName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "LastName can only contain letters")
        String lastName,

        //@NotBlank(message = "LastName cannot be Blank")
        @Pattern(regexp = "\\d{11}", message = "national code must be 11 number")
        String mobileNumber,

        //@NotBlank(message = "National Code cannot be Blank")
        @Pattern(regexp = "\\d{10}", message = "national code must be 10 number")
        String nationalCode,

        //@NotBlank(message = "EmailAddress cannot be Blank")
        @Email(message = "EmailAddress must be a valid email address")
        String emailAddress,

       // @NotBlank(message = "UserName cannot be Blank")
        @Size(min = 4, max = 20, message = "UserName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String userName,

        /*//@NotBlank(message = "Password cannot be Blank")
        @Size(min = 8, max = 8, message = "Password must be exactly {max} characters long")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both letters and numbers")
        String password,*/

        //@NotBlank(message = "Picture Path cannot be Blank")
       /* String picturePath,*/

        @NotNull(message = "customerId cannot be Null")
        @Min(value = 1, message = "customerId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "customerId must be less than or equal to {value}")
        Long customerId
) {
}
