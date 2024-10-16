package spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.service.AdminOperation;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminOperation adminOperation;


    @PutMapping("/add")
    public ResponseEntity<Void> addSubServiceToExpert(@RequestParam Long expertId, @RequestParam Long subServiceId){
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        adminOperation.addSubServiceToExpert(expertId,subServiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);//CREATED or OK????
    }
    @PutMapping("/remove/{expertId}/{subServiceId}")
    public ResponseEntity<Void> removeSubServiceFromExpert(@PathVariable("expertId")  Long expertId,@PathVariable("subServiceId") Long subServiceId){
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        adminOperation.deleteSubServiceFromExpert(expertId,subServiceId);
        return new ResponseEntity<>(HttpStatus.CREATED);//CREATED or OK????
    }
    @PutMapping("confirmed/{id}")
    public ResponseEntity<Void> confirmedExpert(@PathVariable("id") Long expertId) {
        adminOperation.confirmedExpert(expertId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
