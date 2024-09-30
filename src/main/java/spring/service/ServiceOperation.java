package spring.service;

import spring.dto.ChangeServiceDto;

import java.util.List;

public interface ServiceOperation {
    void serviceRegister(String serviceName);
    List<String> findAllService();
    void update(ChangeServiceDto changeServiceDto);
    String findById(Long subServiceId);
}
