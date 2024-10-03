package spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import spring.dto.*;
import spring.exception.ExceptionHandler;
import spring.exception.NotFoundException;
import spring.service.*;

import java.time.ZonedDateTime;

@SpringBootApplication
public class SpringDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);

    }

    @Bean
    CommandLineRunner expertRunner(ExpertOperation expertOperation,
                                   CustomerOperation customerOperation,
                                   ServiceOperation serviceOperation,
                                   SubServiceOperation subServiceOperation,
                                   OrderOperation orderOperation,
                                   AdminOperation adminOperation,
                                   SuggestionOperation suggestionOperation) {
        return args -> {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
//--------------------------------save Expert
           /* RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("zohreh")
                    .lastName("Mohammadi").emailAddress("zo.mohammadi@gmail.com")
                    .mobileNumber("09197847456").nationalCode("0045265772")
                    .userName("zo.mohammadi")
                    .password("zo123456").picturePath("D:\\Java\\picture\\zohre.jpg").build();


            exceptionHandler.handel(() -> expertOperation.register(specialistDto));*/
            //-----add another expert
         /*    RegisterExpertDto specialistDto = RegisterExpertDto.builder().firstName("shima")
                    .lastName("heydari").emailAddress("shima@gmail.com")
                    .mobileNumber("09179948746").nationalCode("0017845718")
                    .userName("sh123")
                    .password("zo123456").picturePath("D:\\Java\\picture\\zohre.jpg").build();


            exceptionHandler.handel(() -> expertOperation.register(specialistDto));*/
//--------------------------------get picture of expert
          /*  try {

                expertOperation.getPicture("zo.mohammadi");
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
           */
//--------------------------------save customer

          /* RegisterCustomerDto customerDto = RegisterCustomerDto.builder().firstName("ali")
                    .lastName("bayati").emailAddress("ali123@123.com")
                    .mobileNumber("09197847776").nationalCode("0145265770")
                    .userName("ali211").password("a1234567").build();

            customerOperation.register(customerDto);*/
//---------------تغییر پسورد-------------------------------------------------------

//-----------------------------------change --- تغییر پسورد مشتری

           /* ChangePasswordDto passwordDto = ChangePasswordDto.builder()
                    .userId(2L).password("123456ss").build();
            customerOperation.changePassword(passwordDto);*/

//------------------------------------change  --- تغییر پسورد متخصص

         /*   ChangePasswordDto passwordDto2 = ChangePasswordDto.builder()
                    .userId(1L).password("123456ss").build();
            expertOperation.changePassword(passwordDto2);
*/
//----------------save Service -------------- اضافه کردن خدمت
/*
            serviceOperation.serviceRegister("Household appliances");
            serviceOperation.serviceRegister("cleaning");*/

//---------------save SubService --------اضافه کردن زیر خدمت

          /*  RegisterSubServiceDto subServiceDto1 = RegisterSubServiceDto.builder()
                    .name("Kitchen gas").description("123 ").BasePrice(2000000.0)
                    .serviceId(1L)
                    .build();

            try {
                subServiceOperation.subServiceRegister(subServiceDto1);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
            //another sub service
            RegisterSubServiceDto subServiceDto = RegisterSubServiceDto.builder()
                    .name("Kitchen Microwave").description("model 2024 ").BasePrice(2000000.0)
                    .serviceId(1L)
                    .build();

            try {
                subServiceOperation.subServiceRegister(subServiceDto);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }

            //another sub service
            RegisterSubServiceDto subServiceDto8 = RegisterSubServiceDto.builder()
                    .name("house cleaning").description("cleaning the house weakly").BasePrice(500000.0)
                    .serviceId(2L)
                    .build();

            try {
                subServiceOperation.subServiceRegister(subServiceDto8);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
*/
//----------------find service نمایش تمام خدمت ها


            /*   serviceOperation.findAllService().forEach(System.out::println);*/


//----------------find SubService  نمایش تمام زیرخدمت ها


            /* subServiceOperation.findAllSubService().forEach(System.out::println);*/


//----------------admin --> تغییر وضعیت متخصص از وضعیت جدید به تایید شده


            /* expertOperation.changeExpertStatus(1L, "Confirmed");*/
            /* expertOperation.changeExpertStatus(3L, "Confirmed");*/


//----------------admin --> اضافه کردن متخصص تایید شده به زیرخدمت


            /*  adminOperation.addSubServiceToExpert(1L, 1L);
              adminOperation.addSubServiceToExpert(1L, 2L);
*/

    //adminOperation.addSubServiceToExpert(3L, 2L);
//---------------admin --> حذف  کردن متخصص از زیرخدمت

            /*adminOperation.deleteSubServiceFromExpert(1L, 1L);*/


//----------------------find service نمایش تمام خدمت ها

            /* serviceOperation.findAllService().forEach(System.out::println);*/


//----------------------find sub service نمایش تمام زیرخدمت های یک خدمت


           /* subServiceOperation.findAllSubServiceOfService(1L)
                    .forEach(System.out::println);*/


//-----------order ---ثبت سفارش

        /*    RegisterOrderDto orderDto1 = RegisterOrderDto.builder()
                    .customerId(2L)
                    .subServiceId(2L)
                    .priceSuggested(5000000.0)
                    .address("fdsgdfs 3434 34")
                    // .serviceDescription("2324jjjjjjjjjjjjjjjjj")
                    .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31
                            , 0, 0, ZonedDateTime.now().getZone()))
                    .build();

            exceptionHandler.handel(() -> orderOperation.orderRegister(orderDto1));*/

 /*           RegisterOrderDto orderDto1 = RegisterOrderDto.builder()
                    .customerId(2L)
                    .subServiceId(3L)
                    .priceSuggested(40000.0)
                    .address("fdsgdfs 3434 34")
                    // .serviceDescription("2324jjjjjjjjjjjjjjjjj")
                    .timeForServiceDone(ZonedDateTime.of(2024, 11, 27, 8, 31
                            , 0, 0, ZonedDateTime.now().getZone()))
                    .build();


            exceptionHandler.handel(() -> orderOperation.orderRegister(orderDto1));*/
//----------------------------------------------------------Faz2--------------------------------------------------

//---------------List orders that expert can  register suggestion for them:
           /* exceptionHandler.handel(() ->suggestionOperation.listOrders(4L)
                    .forEach(projection -> System.out.println(
                            "orderId: " + projection.getId() +
                            "  SubService: " + projection.getSubService() +
                            "  Address: " + projection.getAddress() +
                            "  UserName: " + projection.getUserName() +
                            "  PriceSuggested: " + projection.getPriceSuggested() +
                            "  ServiceDescription: " + projection.getServiceDescription() +
                            "  TimeForServiceDone: " + projection.getTimeForServiceDone()

                    )));*/

//----------------------register Suggestion
         /*   RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                    .expertId(1L).orderId(1L).priceSuggestion(5000000.0).durationOfService("5 saat")
                    .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12, 8, 31
                            , 0, 0, ZonedDateTime.now().getZone()))
                    .build();

            exceptionHandler.handel(() -> suggestionOperation.registerSuggestion(suggestionDto));
*/
            //for expert_id = 3
             /* RegisterSuggestionDto suggestionDto = RegisterSuggestionDto.builder()
                    .expertId(3L).orderId(1L).priceSuggestion(6000000.0).durationOfService("1 saat")
                    .suggestedTimeStartService(ZonedDateTime.of(2024, 11, 12, 8, 31
                            , 0, 0, ZonedDateTime.now().getZone()))
                    .build();

            exceptionHandler.handel(() -> suggestionOperation.registerSuggestion(suggestionDto));*/

//--------------------List Order Suggestion of customer:
          /*  OrderOfCustomerDto orderOfCustomerDto = OrderOfCustomerDto.builder()
                    .customerId(2L)
                    .orderId(1L)
                    .build();

            exceptionHandler.handel(() ->suggestionOperation.listOrderSuggestions(orderOfCustomerDto)
                    .forEach(projection -> System.out.println(
                            "Id: " + projection.getId() +
                            "  DurationOfService: " + projection.getDurationOfService() +
                            "  PriceSuggested: " + projection.getPriceSuggested() +
                            "  SuggestedTimeStartService: " + projection.getSuggestedTimeStartService() +
                            "  UserName: " + projection.getUserName() +
                            "  FirstName: " + projection.getFirstName() +
                            "  LastName: " + projection.getLastName() +
                            "  Score: " + projection.getScore()
                    )));*/
//--------------------select suggestion of above list: (change the order status and add expert_id to Order table)

           /* exceptionHandler.handel(() -> suggestionOperation.selectSuggestionOfOrder(4L));*/

//-------------------change status from wanting for coming Expert to your Place -->to Started
           /* exceptionHandler.handel(() ->orderOperation.changeOrderStatusToStarted(1L));*/

//------------------- change status from Started to Done
            /* exceptionHandler.handel(() ->orderOperation.changeOrderStatusToDone(1L));*/
        };
    }


}
