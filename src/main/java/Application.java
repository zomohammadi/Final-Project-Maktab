import customeException.NotFoundException;
import dto.*;
import util.ApplicationContext;

import java.time.ZonedDateTime;

public class Application {
    public static void main(String[] args) {
        ApplicationContext instance = ApplicationContext.getInstance();

        //----------------save Expert
RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("zohreh")
                .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com")
                .mobileNumber("09197847756").nationalCode("0045265772")
                .userName("zo.mohammadi")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        instance.getExpertOperation().register(specialistDto);



        // ----save another user with duplicate national code

    RegisterExpertDto specialistDto2 = RegisterExpertDto.builder().firstName("sara")
                .lastName("bayat").emailAddress("s.bayat@yahoo.com")
                .mobileNumber("09197847756").nationalCode("0045265773")
                .userName("zo.mohammadi")//.userName("s.bayat")
                .password("zo123456").picturePath("D:\\Java\\picture\\pic.jpg").build();

        instance.getExpertOperation().register(specialistDto2);



//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }

        //----------------get picture of expert
try {

            instance.getExpertOperation().getPicture("zo.mohammadi");
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }



        //----------------save customer
 RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                .lastName("bayati").emailAddress("ali123@123.com")
                .mobileNumber("09197847756").nationalCode("0045265770")
                .userName("ali111").password("aaaaaaa1").build();

        instance.getCustomerOperation().register(customerDto);


        //---------------تغییر پسورد------------------------------

        //----------change --- تغییر پسورد مشتری

ChangePasswordDto passwordDto = ChangePasswordDto.builder()
                .userId(2L).password("123456ss").build();
        instance.getCustomerOperation().changePassword(passwordDto);


        //----------change  --- تغییر پسورد متخصص

      ChangePasswordDto passwordDto2 = ChangePasswordDto.builder()
                .userId(1L).password("123456ss").build();
        instance.getExpertOperation().changePassword(passwordDto2);


        //----------------save Service -------------- اضافه کردن خدمت

 instance.getServiceOperation().serviceRegister("Household appliances");
        instance.getServiceOperation().serviceRegister("cleaning");


        //---------------save SubService --------اضافه کردن زیر خدمت

 RegisterSubServiceDto subServiceDto1 = RegisterSubServiceDto.builder()
                .name("Kitchen gas").description("123 ").BasePrice(2000000.0)
                .serviceId(1L)
                .build();

        try {
            instance.getSubServiceOperation().subServiceRegister(subServiceDto1);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
        //another sub service
        RegisterSubServiceDto subServiceDto = RegisterSubServiceDto.builder()
                .name("Kitchen Microwave").description("model 2024 ").BasePrice(2000000.0)
                .serviceId(1L)
                .build();

        try {
            instance.getSubServiceOperation().subServiceRegister(subServiceDto);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }


        //another sub service
 RegisterSubServiceDto subServiceDto8 = RegisterSubServiceDto.builder()
                .name("house cleaning").description("cleaning the house weakly").BasePrice(500000.0)
                .serviceId(2L)
                .build();

        try {
            instance.getSubServiceOperation().subServiceRegister(subServiceDto8);
        } catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }


        //----------------find service نمایش تمام خدمت ها

        instance.getServiceOperation().findAllService().forEach(System.out::println);




        //----------------find SubService  نمایش تمام زیرخدمت ها

       instance.getSubServiceOperation().findAllSubService().forEach(System.out::println);



        //----------------admin --> تغییر وضعیت متخصص از وضعیت جدید به تایید شده

 instance.getExpertOperation().changeExpertStatus(1L, "Confirmed");



        //----------------admin --> اضافه کردن متخصص تایید شده به زیرخدمت
 instance.getAdminOperation().addSubServiceToExpert(1L, 1L);

        instance.getAdminOperation().addSubServiceToExpert(1L, 2L);


        //---------------admin --> حذف  کردن متخصص از زیرخدمت

       instance.getAdminOperation().deleteSubServiceFromExpert(1L, 1L);



        //----------------------find service نمایش تمام خدمت ها

   instance.getServiceOperation().findAllService().forEach(System.out::println);


        //----------------------find sub service نمایش تمام زیرخدمت های یک خدمت

     instance.getSubServiceOperation().findAllSubServiceOfService(1L)
                .forEach(System.out::println);


        //-----------order ---ثبت سفارش

 RegisterOrderDto orderDto1 = RegisterOrderDto.builder()
                .customerId(2L)
                .subServiceId(1L)
                .priceSuggested(2000000.0)
                .address("fdsgdfs 3434 34")
                  // .serviceDescription("2324jjjjjjjjjjjjjjjjj")
                .timeForServiceDone(ZonedDateTime.of(2024, 10, 24, 22, 31
                        , 0, 0, ZonedDateTime.now().getZone()))
                .build();

        instance.getOrderOperation().orderRegister(orderDto1);


        // ----------------- اضافه کردن سفارش با اطلاعات نادرست-----------------
   RegisterOrderDto orderDto = RegisterOrderDto.builder()
                .customerId(3L)
                .subServiceId(3L)
                .priceSuggested(100000.0)
                .address("fdsgdfs 3434 34")
                .timeForServiceDone(ZonedDateTime.of(2024, 10, 24, 22, 31
                        , 0, 0, ZonedDateTime.now().getZone()))
                .build();

        instance.getOrderOperation().orderRegister(orderDto);


        //------------------------------------------Crud--------------------------------------------------------------
        //----------------------------------SubService ---------------------------------------------------------------
        //------------update  تغییر نام یا توضیحات یا قیمت پایه زیرخدمت
        //dto بدون ای دی و با نام تکراری
        //ChangeSubServiceDto subServiceDto1 = ChangeSubServiceDto.builder().name("Kitchen gas").build();

        //آپدیت فقط نام

 ChangeSubServiceDto subServiceDto4 = ChangeSubServiceDto.builder().name("Refrigerator repair")
                .subServiceId(1L).build();
        instance.getSubServiceOperation().update(subServiceDto4);


        //آپدیت توضیحات و قیمت

ChangeSubServiceDto subServiceDto5 = ChangeSubServiceDto.builder().description("fg fg fg fg  123")
                .BasePrice(3000000.0)
                .subServiceId(1L).build();
        instance.getSubServiceOperation().update(subServiceDto5);


        //آپدیت فقط قیمت

 ChangeSubServiceDto subServiceDto6 = ChangeSubServiceDto.builder()
                .BasePrice(5000000.0)
                .subServiceId(1L).build();
        instance.getSubServiceOperation().update(subServiceDto6);


        //------------------find by id
 System.out.println(instance.getSubServiceOperation().findById(1L));


        //----------------------------------Expert ---------------------------------------------------------------
        //---------update

  ChangeExpertDto expertDto = ChangeExpertDto.builder().expertId(1L).firstName("zahra")
                .emailAddress("zahra123@gmail.com").build();
        instance.getExpertOperation().update(expertDto);


        //----------find by id
System.out.println(instance.getExpertOperation().findById(3L));


        //----------------------------------Customer --------------------------------------------------------------
        //---------update
ChangeCustomerDto customerDto5 = ChangeCustomerDto.builder().customerId(2L).firstName("alireza")
                .emailAddress("alireza123ali@gmail.com").build();
        instance.getCustomerOperation().update(customerDto5);


        //----------find by id

System.out.println(instance.getCustomerOperation().findById(2L));

        //----------------------------------Service --------------------------------------------------------------
        //---------update
ChangeServiceDto serviceDto = ChangeServiceDto.builder().ServiceId(1L).name("sdf").build();
        instance.getServiceOperation().update(serviceDto);


        ////----------find by id

System.out.println(instance.getServiceOperation().findById(1L));


    }
}

//        } catch (FoundEmailAddressException | FoundMobileNumberException |
//                 FoundNationalCodeException | FoundUserNameException e) {
//            System.err.println(e.getMessage());
//        }
//------------------delete by id
//  instance.getSubServiceOperation().delete(1l);


//        RegisterOrderDto orderDto = RegisterOrderDto.builder()
//                .customerId(1l)
//                .subServiceId(1l)
//                .priceSuggested(100000.0)
//                .address("fdsgdfs 3434 34")
//                .timeForServiceDone(ZonedDateTime.of(2024, 9, 24, 22, 31
//                        , 0, 0, ZonedDateTime.now().getZone()))
//                .build();
