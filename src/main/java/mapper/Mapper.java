package mapper;

import dto.RegisterExpertDto;
import entity.Profile;
import entity.Expert;

public class Mapper {
    public static Expert convertExpertDtoToEntity(RegisterExpertDto dto) {
        return Expert.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .emailAddress(dto.emailAddress())
                .profile(Profile.builder()
                        .userName(dto.userName())
                        .password(dto.password())
                        .build()).score(dto.score()).
                build();
        //.picture(dto.picture())

    }

    public static RegisterExpertDto convertExpertToDto(Expert expert) {
        /*return new RegisterExpertDto(expert.getFirstName(), expert.getLastName()
                , expert.getEmailAddress()
                , expert.getProfile().getUserName(), expert.getProfile().getPassword()
                , expert.getScore());*/

        return RegisterExpertDto.builder().firstName(expert.getFirstName()).lastName(expert.getLastName())
                .emailAddress(expert.getEmailAddress()).userName(expert.getProfile().getUserName())
                .password(expert.getProfile().getPassword()).score(expert.getScore()).build();
        //.picture(expert.picture())


    }


}
