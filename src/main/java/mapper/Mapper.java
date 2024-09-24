package mapper;

import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import dto.RegisterSubServiceDto;
import dto.ResponceSubServiceDto;
import entity.Customer;
import entity.Expert;
import entity.SubService;
import entity.Service;

public class Mapper {
    public static Expert convertExpertDtoToEntity(RegisterExpertDto dto) {
        return Expert.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .emailAddress(dto.emailAddress())
                .mobileNumber(dto.mobileNumber())
                .nationalCode(dto.nationalCode())
                .userName(dto.userName())
                .password(dto.password())
                //.score(dto.score())
                .build();
        //.picture(dto.picture())

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

    public static ResponceSubServiceDto convertSubServiceToDto(SubService subService) {
        return ResponceSubServiceDto.builder()
                .name(subService.getName())
                .description(subService.getDescription())
                .BasePrice(subService.getBasePrice())
                .serviceName(subService.getService().getName())
                .build();

    }
}
