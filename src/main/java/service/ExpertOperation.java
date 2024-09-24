package service;

import dto.RegisterExpertDto;


public interface ExpertOperation {
    void register(RegisterExpertDto expertDto);
    void getPicture(String userName);
    void changeExpertStatus(Long expertId, String status);
}
