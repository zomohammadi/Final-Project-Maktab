package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.service.AdminOperation;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminOperation adminOperation;

    @PutMapping("/add")
    public ResponseEntity<Void> addSubServiceToExpert(Long expertId, Long subServiceId){
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        adminOperation.addSubServiceToExpert(expertId,subServiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);//CREATED or OK????
    }
    @PutMapping("/remove")
    public ResponseEntity<Void> removeSubServiceFromExpert(Long expertId, Long subServiceId){
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        adminOperation.deleteSubServiceFromExpert(expertId,subServiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);//CREATED or OK????
    }
}
