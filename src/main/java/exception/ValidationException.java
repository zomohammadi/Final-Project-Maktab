package exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class ValidationException extends RuntimeException {
    private Set<String> errors;

    public ValidationException(Set<String> errors) {
        this.errors = errors;
    }

    //private Set<ConstraintViolation<?>> violations;
   /* public ValidationException(Set<?> violations) {
        this.violations = (Set<ConstraintViolation<?>>) violations;
    }*/
}
