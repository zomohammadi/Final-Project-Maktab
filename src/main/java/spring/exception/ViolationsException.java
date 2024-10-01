package spring.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class ViolationsException extends RuntimeException{
    private Set<ConstraintViolation<?>> violations;

    public ViolationsException(Set<?> violations) {
        this.violations = (Set<ConstraintViolation<?>>) violations;
    }
}
