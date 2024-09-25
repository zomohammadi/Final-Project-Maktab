package mapper;

import dto.*;
import entity.*;

//@SuppressWarnings("all")
public class Mapper {
    public static class ConvertDtoToEntity {
        public static Expert convertExpertDtoToEntity(RegisterExpertDto dto) {
            return Expert.builder()
                    .firstName(dto.firstName())
                    .lastName(dto.lastName())
                    .emailAddress(dto.emailAddress())
                    .mobileNumber(dto.mobileNumber())
                    .nationalCode(dto.nationalCode())
                    .userName(dto.userName())
                    .password(dto.password())
                    .build();


        }

        public static Customer convertCustomerDtoToEntity(RegisterCustomerDto customerDto) {
            return Customer.builder().firstName(customerDto.firstName()).lastName(customerDto.lastName())
                    .emailAddress(customerDto.emailAddress()).userName(customerDto.userName())
                    .mobileNumber(customerDto.mobileNumber())
                    .nationalCode(customerDto.nationalCode())
                    .password(customerDto.password()).build();
        }

        public static SubService convertSubServiceDtoToEntity(RegisterSubServiceDto subServiceDto, Service service) {
            return SubService.builder()
                    .name(subServiceDto.name())
                    .description(subServiceDto.description())
                    .BasePrice(subServiceDto.BasePrice())
                    .service(service)
                    .build();
        }

        public static Orders convertOrderDtoToEntity(RegisterOrderDto orderDto, Customer customer, SubService subService) {
            Orders.OrdersBuilder<?, ?> builder = Orders.builder()
                    .subService(subService)
                    .customer(customer)
                    .priceSuggested(orderDto.priceSuggested())
                    .address(orderDto.address())
                    .timeForServiceDone(orderDto.timeForServiceDone());
            if (orderDto.serviceDescription() != null
                /*|| !orderDto.serviceDescription().equals("")
                || !orderDto.serviceDescription().equals(" ")*/)
                builder.serviceDescription(orderDto.serviceDescription());
            return builder.build();
        }

        public static SubService convertChangeSubServiceDtoToEntity(ChangeSubServiceDto subServiceDto) {
            SubService.SubServiceBuilder<?, ?> builder = SubService.builder().id(subServiceDto.serviceId());
            if (subServiceDto.name() != null)
                builder.name(subServiceDto.name());
            if (subServiceDto.description() != null)
                builder.description(subServiceDto.description());
            if (subServiceDto.BasePrice() != null)
                builder.BasePrice(subServiceDto.BasePrice());
            return builder.build();
        }
    }

    public static class ConvertEntityToDto {
        public static ResponceSubServiceDto convertSubServiceToDto(SubService subService) {
            return ResponceSubServiceDto.builder()
                    .name(subService.getName())
                    .description(subService.getDescription())
                    .BasePrice(subService.getBasePrice())
                    .serviceName(subService.getService().getName())
                    .build();

        }

    }


}
