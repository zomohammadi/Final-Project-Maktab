import dto.RegisterExpertDto;
import util.ApplicationContext;

import java.time.ZonedDateTime;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();
        RegisterExpertDto specialistDto = new RegisterExpertDto("zohre", "mohammdi"
                ,  "zo.mohammadi@gmail.com", "zo.mohammadi", "zo123456"
                , 0);
        instance.getExpertService().save(specialistDto);

    }
}
