package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);

    }
}
//---------------List orders that expert can  register suggestion for them:
      /*      exceptionHandler.handel(() ->suggestionOperation.listOrders(1L)
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
          /*//  OrderOfCustomerDto = OrderOfCustomerDto.builder()
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
   /*     };
    }


}*/
