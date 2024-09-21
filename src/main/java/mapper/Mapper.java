package mapper;

import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import entity.Customer;
import entity.Expert;

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

    public static RegisterExpertDto convertExpertToDto(Expert expert) {
        /*return new RegisterExpertDto(expert.getFirstName(), expert.getLastName()
                , expert.getEmailAddress()
                , expert.getProfile().getUserName(), expert.getProfile().getPassword()
                , expert.getScore());*/

        return RegisterExpertDto.builder().firstName(expert.getFirstName()).lastName(expert.getLastName())
                .emailAddress(expert.getEmailAddress()).userName(expert.getUserName())
                .password(expert.getPassword())
                // .score(expert.getScore())
                .build();
        //.picture(expert.picture())


    }


    public static Customer convertCustomerDtoToEntity(RegisterCustomerDto customerDto) {
        return Customer.builder().firstName(customerDto.firstName()).lastName(customerDto.lastName())
                .emailAddress(customerDto.emailAddress()).userName(customerDto.userName())
                .mobileNumber(customerDto.mobileNumber())
                .nationalCode(customerDto.nationalCode())
                .password(customerDto.password()).build();
    }
}
