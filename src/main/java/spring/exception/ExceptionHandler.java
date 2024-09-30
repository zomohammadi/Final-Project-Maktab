package spring.exception;

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
            e.getErrors().forEach(System.out::println);
            //System.out.println("\u001B[34m" + e.getErrors() + "\u001B[0m");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
