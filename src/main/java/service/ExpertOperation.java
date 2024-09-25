package service;

import dto.ChangeExpertDto;
import dto.ChangePasswordDto;
import dto.RegisterExpertDto;
import dto.ResponceExpertDto;


public interface ExpertOperation {
    void register(RegisterExpertDto expertDto);

    void getPicture(String userName);

    void changeExpertStatus(Long expertId, String status);

    void changePassword(ChangePasswordDto passwordDto);

    void update(ChangeExpertDto expertDto);

    ResponceExpertDto findById(Long expertId);
}
