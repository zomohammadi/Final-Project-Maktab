package service;

import dto.RegisterExpertDto;


public interface ExpertService {
    void register(RegisterExpertDto expertDto);
    void getPicture(String userName);
}
