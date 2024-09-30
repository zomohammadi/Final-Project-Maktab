package spring.exception;

import jakarta.persistence.EntityNotFoundException;

public class ExceptionHandler {
    public void handel(Runnable runnable) {
        try {
            runnable.run();
        } /*
        //M.F
        catch (ValidationException e) {
            for (ConstraintViolation<?> v : e.getViolations())
                System.out.println("\u001B[34m" + v.getMessage() + "\u001B[0m");


        }*/ catch (ValidationException e) {
            e.getErrors().forEach(error->System.out.println("\u001B[33m" + error + "\u001B[0m"));
        } catch (FoundException | EntityNotFoundException e) {
            System.out.println("\u001B[33m" + e.getMessage() + "\u001B[0m");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
