package service;

import java.util.List;

public interface ServiceOperation {
    void workRegister(String workName);
    List<String> findAllWork();
}
