package service;

import java.util.List;

public interface ServiceOperation {
    void serviceRegister(String serviceName);
    List<String> findAllService();
}
