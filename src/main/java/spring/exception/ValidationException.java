package spring.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class ValidationException extends RuntimeException {
    private final Set<String> errors;

    public ValidationException(Set<String> errors) {
        super(errors.isEmpty() ? "Validation failed with unknown errors." : String.join(", ", errors));
        this.errors = errors;
    }
}

//M.f
//
//@Getter
//public class ValidationException2 extends RuntimeException {
//
//    private Set<ConstraintViolation<?>> violations;
//    public ValidationException2(Set<?> violations) {
//        this.violations = (Set<ConstraintViolation<?>>) violations;
//    }
//}*/


    /*Set<ConstraintViolation<RegisterExpertDto>> violations = validator.validate(expertDto);
        if (!violations.isEmpty() ) {
                throw new ValidationException2(violations);
                }*/