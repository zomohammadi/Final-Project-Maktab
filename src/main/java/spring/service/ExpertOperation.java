package spring.service;

import spring.dto.ChangeExpertDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterExpertDto;
import spring.dto.ResponceExpertDto;


public interface ExpertOperation {
    void register(RegisterExpertDto expertDto);

    void getPicture(String userName);
    //void getPictureById(Long id);

    void changeExpertStatus(Long expertId, String status);

    void changePassword(ChangePasswordDto passwordDto);

    void update(ChangeExpertDto expertDto);

    ResponceExpertDto findById(Long expertId);
}
