package spring.mapper;


import spring.dto.*;
import spring.entity.*;

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
                    .basePrice(subServiceDto.basePrice())
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
            SubService.SubServiceBuilder<?, ?> builder = SubService.builder().id(subServiceDto.subServiceId());
            if (subServiceDto.name() != null)
                builder.name(subServiceDto.name());
            if (subServiceDto.description() != null)
                builder.description(subServiceDto.description());
            if (subServiceDto.basePrice() != null)
                builder.basePrice(subServiceDto.basePrice());
            return builder.build();
        }

        public static Expert convertChangeExpertDtoToEntity(ChangeExpertDto expertDto) {
            Expert.ExpertBuilder<?, ?> builder = Expert.builder().id(expertDto.expertId());
            if (expertDto.firstName() != null)
                builder.firstName(expertDto.firstName());
            if (expertDto.lastName() != null)
                builder.lastName(expertDto.lastName());
            if (expertDto.nationalCode() != null)
                builder.nationalCode(expertDto.nationalCode());
            if (expertDto.mobileNumber() != null)
                builder.mobileNumber(expertDto.mobileNumber());
            if (expertDto.emailAddress() != null)
                builder.emailAddress(expertDto.emailAddress());
            if (expertDto.userName() != null)
                builder.userName(expertDto.userName());
            return builder.build();
        }

        public static Customer convertChangeCustomerDtoToEntity(ChangeCustomerDto customerDto) {
            Customer.CustomerBuilder<?, ?> builder = Customer.builder().id(customerDto.customerId());
            if (customerDto.firstName() != null)
                builder.firstName(customerDto.firstName());
            if (customerDto.lastName() != null)
                builder.lastName(customerDto.lastName());
            if (customerDto.nationalCode() != null)
                builder.nationalCode(customerDto.nationalCode());
            if (customerDto.mobileNumber() != null)
                builder.mobileNumber(customerDto.mobileNumber());
            if (customerDto.emailAddress() != null)
                builder.emailAddress(customerDto.emailAddress());
            if (customerDto.userName() != null)
                builder.userName(customerDto.userName());
            return builder.build();
        }

        public static Service convertChangeServiceDtoToEntity(ChangeServiceDto changeServiceDto) {
            return Service.builder().id(changeServiceDto.ServiceId()).name(changeServiceDto.name()).build();
        }
        public static Suggestion convertSuggestionDtoToEntity(RegisterSuggestionDto suggestionDto
                ,Expert expert,Orders order){
            return Suggestion.builder()
                    .expert(expert)
                    .order(order)
                    .durationOfService(suggestionDto.durationOfService())
                    .priceSuggested(suggestionDto.priceSuggestion())
                    .suggestedTimeStartService(suggestionDto.suggestedTimeStartService())
                    .build();
        }
    }

    public static class ConvertEntityToDto {
        public static ResponceSubServiceDto convertSubServiceToDto(SubService subService) {
            return ResponceSubServiceDto.builder()
                    .name(subService.getName())
                    .description(subService.getDescription())
                    .basePrice(subService.getBasePrice())
                    .serviceName(subService.getService().getName())
                    .build();
        }

        public static ResponceExpertDto convertExpertToDto(Expert expert) {
            return ResponceExpertDto.builder()
                    .firstName(expert.getFirstName())
                    .lastName(expert.getLastName())
                    .emailAddress(expert.getEmailAddress())
                    .mobileNumber(expert.getMobileNumber())
                    .nationalCode(expert.getNationalCode())
                    .userName(expert.getUserName())
                    .score(expert.getScore())
                    .status(expert.getStatus())
                    .build();
        }

        public static ResponceCustomerDto convertCustomerToDto(Customer customer) {
            return ResponceCustomerDto.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .emailAddress(customer.getEmailAddress())
                    .mobileNumber(customer.getMobileNumber())
                    .nationalCode(customer.getNationalCode())
                    .userName(customer.getUserName())
                    .build();
        }
    }
}
