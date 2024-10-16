package spring.dto;

import jakarta.validation.constraints.*;

public record SuggestionBriefDto (
        @NotNull(message = "suggestionId cannot be Null")
        @Min(value = 1, message = "suggestionId must be greater than or equal to {value}")
        @Max(value = Long.MAX_VALUE, message = "suggestionId must be less than or equal to {value}")
        Long suggestionId,

        @NotNull(message = "durationOfService cannot be Null")
        @Min(value = 1, message = "durationOfService must be greater than or equal to {value}")
        @Max(value = Integer.MAX_VALUE, message = "durationOfService must be less than or equal to {value}")
        int durationOfService,

        @NotNull(message = "Base Price cannot be Null")
        @Min(value = 100000, message = "Base Price must be greater Than {value} numbers ")
        @Max(value = 900000000, message = "Base Price must be less Than {value} numbers ")
        Double priceSuggested,

        @NotBlank(message = "UserName cannot be Blank")
        @Size(min = 4, max = 20, message = "UserName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        String userName,
        @NotBlank(message = "FirstName cannot be Blank")
        @Size(min = 3, max = 30, message = "FirstName must be less than {max} characters " +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "FirstName can only contain letters")
        String firstName,
        @NotBlank(message = "LastName cannot be Blank")
        @Size(min = 3, max = 40, message = "LastName must be less than {max} characters" +
                                           "and greater Than {min} characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "LastName can only contain letters")
        String lastName,

        @NotNull(message = "score cannot be Null")
        int score

){
}
