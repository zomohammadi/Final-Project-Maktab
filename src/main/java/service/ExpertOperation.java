package service;

import dto.ChangePasswordDto;
import dto.RegisterExpertDto;


public interface ExpertOperation {
    void register(RegisterExpertDto expertDto);
    void getPicture(String userName);
    void changeExpertStatus(Long expertId, String status);
    void changePassword(ChangePasswordDto passwordDto);
}
