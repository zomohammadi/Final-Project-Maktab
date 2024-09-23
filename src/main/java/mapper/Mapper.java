package mapper;

import dto.RegisterCustomerDto;
import dto.RegisterExpertDto;
import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;
import entity.Customer;
import entity.Expert;
import entity.SubWork;
import entity.Work;

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

    public static SubWork convertSubWorkDtoToEntity(RegisterSubWorkDto subWorkDto, Work work) {
        return SubWork.builder()
                .name(subWorkDto.name())
                .description(subWorkDto.description())
                .BasePrice(subWorkDto.BasePrice())
                .work(work)
                .build();
    }

    public static ResponceSubWorkDto convertSubWorkToDto(SubWork subWork) {
        return ResponceSubWorkDto.builder()
                .name(subWork.getName())
                .description(subWork.getDescription())
                .BasePrice(subWork.getBasePrice())
                .workName(subWork.getWork().getName())
                .build();

    }
}
