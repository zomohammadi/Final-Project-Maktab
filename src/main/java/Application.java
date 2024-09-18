import customeException.NotFoundException;
import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import util.ApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();

        //----------------save Expert
       /* RegisterExpertDto specialistDto =  RegisterExpertDto.builder().firstName("zohreh")
                .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com").userName("zo.mohammadi")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        instance.getExpertService().register(specialistDto);*/

        //----------------get picture of expert
       /* try {

            instance.getExpertService().getPicture("zo.mohammadi");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }*/
       //----------------save customer
        RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                .lastName("bayat").emailAddress("ali123@yahooo.com")
                .userName("ali123").password("aaaaaaa1").build();

        instance.getCustomerService().register(customerDto);
    }
}
