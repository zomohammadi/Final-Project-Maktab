import customeException.NotFoundException;
import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import dto.RegisterOrderDto;
import dto.RegisterSubServiceDto;
import util.ApplicationContext;

import java.time.ZonedDateTime;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();

        //----------------save Expert
        /*RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("zohreh")
                .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com")
                .mobileNumber("09197847756").nationalCode("0045265772")
                .userName("zo.mohammadi")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        instance.getExpertOperation().register(specialistDto);*/


        // ----save another user with duplicate national code

       /* RegisterExpertDto specialistDto2 = RegisterExpertDto.builder().firstName("sara")
                .lastName("bayat").emailAddress("s.bayat@yahoo.com")
                .mobileNumber("09197847756").nationalCode("0045265773")
                .userName("zo.mohammadi")//.userName("s.bayat")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        instance.getExpertOperation().register(specialistDto2);*/


//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }

        //----------------get picture of expert
        /*try {

            instance.getExpertOperation().getPicture("zo.mohammadi");
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }*/


        //----------------save customer
        /*RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                .lastName("bayati").emailAddress("ali123@123.com")
                .mobileNumber("09197847756").nationalCode("0045265770")
                .userName("ali111").password("aaaaaaa1").build();

        instance.getCustomerOperation().register(customerDto);*/


        //----------------save Service
        /*
        instance.getServiceOperation().serviceRegister("Household appliances");
*/
        //---------------save SubService

      /*  RegisterSubServiceDto subServiceDto1 = RegisterSubServiceDto.builder()
                .name("Kitchen gas").description("123 ").BasePrice(2000000.0)
                .serviceId(1l)
                .build();

        try {
            instance.getSubServiceOperation().subServiceRegister(subServiceDto1);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
        //another sub service
        RegisterSubServiceDto subServiceDto = RegisterSubServiceDto.builder()
                .name("Kitchen Microwave").description("model 2024 ").BasePrice(2000000.0)
                .serviceId(1l)
                .build();

        try {
            instance.getSubServiceOperation().subServiceRegister(subServiceDto);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
*/

        //----------------find service نمایش تمام خدمت ها
/*

        instance.getServiceOperation().findAllService().forEach(System.out::println);
*/


        //----------------find SubService  نمایش تمام زیرخدمت ها

      /*
       instance.getSubServiceOperation().findAllSubService().forEach(System.out::println);
*/

        //----------------admin --> تغییر وضعیت متخصص از وضعیت جدید به تایید شده
/*

        instance.getExpertOperation().changeExpertStatus(1l, "Confirmed");
*/


        //----------------admin --> اضافه کردن متخصص تایید شده به زیرخدمت
     /*   instance.getAdminOperation().addSubServiceToExpert(1l, 1l);

        instance.getAdminOperation().addSubServiceToExpert(1l, 2l);
*/

        //---------------admin --> حذف  کردن متخصص از زیرخدمت

       /*
       instance.getAdminOperation().deleteSubServiceFromExpert(1l, 1l);
*/
        //-----------order ---ثبت سفارش

//        RegisterOrderDto orderDto = RegisterOrderDto.builder()
//                .customerId(1l)
//                .subServiceId(1l)
//                .priceSuggested(100000.0)
//                .address("fdsgdfs 3434 34")
//                .timeForServiceDone(ZonedDateTime.of(2024, 9, 24, 22, 31
//                        , 0, 0, ZonedDateTime.now().getZone()))
//                .build();

       /* RegisterOrderDto orderDto = RegisterOrderDto.builder()
                .customerId(2l)
                .subServiceId(1l)
                .priceSuggested(100000.0)
                .address("fdsgdfs 3434 34")
                .timeForServiceDone(ZonedDateTime.of(2024, 10, 24, 22, 31
                        , 0, 0, ZonedDateTime.now().getZone()))
                .build();

        instance.getOrderOperation().orderRegister(orderDto);*/


    }
}

//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }
