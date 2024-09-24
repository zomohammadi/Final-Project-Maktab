import customeException.NotFoundException;
import dto.RegisterSubWorkDto;
import util.ApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();

        //----------------save Expert
        /*RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("zohreh")
                .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com")
                .mobileNumber("09197847756").nationalCode("0045265772")
                .userName("zo.mohammadi")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

            instance.getExpertService().register(specialistDto);*/

        // ----save another user with duplicate national code

      /*  RegisterExpertDto specialistDto2 = RegisterExpertDto.builder().firstName("sara")
                .lastName("bayat").emailAddress("s.bayat@yahoo.com")
                .mobileNumber("09197847756").nationalCode("0045265773")
                .userName("zo.mohammadi")//.userName("s.bayat")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

            instance.getExpertService().register(specialistDto2);



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
        /*RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                .lastName("bayati").emailAddress("ali123@123.com")
                .mobileNumber("09197847756").nationalCode("0045265770")
                .userName("ali111").password("aaaaaaa1").build();

        instance.getCustomerService().register(customerDto);*/


        //----------------save Work
       /* instance.getServiceOperation().workRegister("Household appliances");*/

        //---------------save SubWork

       /* RegisterSubWorkDto subWorkDto1 = RegisterSubWorkDto.builder()
                .name("Kitchen gaس").description("123 ").BasePrice(2000000.0)
                .workId(1l)
                .build();

        try {
            instance.getSubServiceOperation().subWorkRegister(subWorkDto1);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
        //another sub service
        RegisterSubWorkDto subWorkDto = RegisterSubWorkDto.builder()
                .name("Kitchen Microwave").description("model 2024 ").BasePrice(2000000.0)
                .workId(1l)
                .build();

        try {
            instance.getSubServiceOperation().subWorkRegister(subWorkDto);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }*/

        //----------------find service نمایش تمام خدمت ها

       /* instance.getServiceOperation().findAllWork().forEach(System.out::println);*/

        //----------------find subWork  نمایش تمام زیرخدمت ها

        /*instance.getSubServiceOperation().findAllSubWork().forEach(System.out::println);*/

        //----------------admin --> تغییر وضعیت متخصص از وضعیت جدید به تایید شده

        /*instance.getExpertService().changeExpertStatus(1l,"Confirmed");*/

        //----------------admin --> اضافه کردن متخصص تایید شده به زیرخدمت
      /*   instance.getAdminService().addSubWorkToExpert(1l, 1l);

        instance.getAdminService().addSubWorkToExpert(1l, 2l);*/

        //---------------admin --> حذف  کردن متخصص از زیرخدمت

      //  instance.getAdminService().deleteSubWorkFromExpert(1l, 1l);

        //-----------order


    }
}

//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }
