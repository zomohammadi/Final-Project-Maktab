package spring.service;

import spring.dto.ChangeServiceDto;
import spring.dto.RegisterServiceDto;
import spring.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceOperation {
    void serviceRegister(RegisterServiceDto serviceDto);
    List<String> findAllService();
    void update(ChangeServiceDto changeServiceDto);
    Service findById(Long subServiceId);
}
