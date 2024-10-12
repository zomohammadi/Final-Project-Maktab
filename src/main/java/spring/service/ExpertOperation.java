package spring.service;

import spring.dto.ChangeExpertDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterExpertDto;
import spring.entity.Expert;

import java.io.File;


public interface ExpertOperation {
    void register(RegisterExpertDto expertDto);
   File getPictureFileByUserName(String userName);
    void confirmedExpert(Long expertId);

    void changePassword(ChangePasswordDto passwordDto);

    void update(ChangeExpertDto expertDto);

    Expert findById(Long expertId);
}
