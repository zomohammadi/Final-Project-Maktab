package spring.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;

public class ExceptionHandler {
    public void handel(Runnable runnable) {
        try {
            runnable.run();
        } catch (ValidationException e) {
            e.getErrors().forEach(error -> System.out.println("\u001B[33m" + error + "\u001B[0m"));
        } catch (ViolationsException e) {
            for (ConstraintViolation<?> v : e.getViolations())
                System.out.println(v.getMessage());

        } catch (FoundException |
                 EntityNotFoundException | NotFoundException | EntityExistsException e) {
            System.out.println("\u001B[33m" + e.getMessage() + "\u001B[0m");
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
/*
public class ExceptionHandler2 {
    public void handel(Runnable runnable) {
        try {
            runnable.run();
        } catch (ValidationException2 e) {
            for (ConstraintViolation<?> v : e.getViolations())
                System.out.println(v.getMessage());

        }
    }
}*/
