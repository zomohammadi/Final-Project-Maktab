import customeException.*;
import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import util.ApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();

        //----------------save Expert
      /*  RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("zohreh")
                .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com")
                .mobileNumber("09197847756").nationalCode("0045265772")
                .userName("zo.mohammadi")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        try {
            instance.getExpertService().register(specialistDto);
        } catch (FoundException e) {
            System.err.println(e.getMessage());
        }*/

//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }
        // ----save another user with duplicate national code

      /*  RegisterExpertDto specialistDto2 = RegisterExpertDto.builder().firstName("sara")
                .lastName("bayat").emailAddress("s.bayat@yahoo.com")
                .mobileNumber("09197847756").nationalCode("0045265773")
                .userName("zo.mohammadi")//.userName("s.bayat")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();
        try {
            instance.getExpertService().register(specialistDto2);
        } catch (FoundException e) {
            System.err.println(e.getMessage());
        }*/


//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }

        //----------------get picture of expert
        /*try {

            instance.getExpertService().getPicture("zo.mohammadi");
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }*/

        //----------------save customer
        RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                .lastName("bayat").emailAddress("ali123@yahooo.com")
                .mobileNumber("09197847753").nationalCode("0045265773")
                .userName("ali123").password("aaaaaaa1").build();

        try {
            instance.getCustomerService().register(customerDto);
        } catch (FoundException e) {
            System.err.println(e.getMessage());
        }

        //----------------

    }
}
