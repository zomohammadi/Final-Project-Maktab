package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.ChangeServiceDto;
import spring.dto.RegisterServiceDto;
import spring.entity.Service;
import spring.service.ServiceOperation;

import java.util.List;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceOperation serviceOperation;

    @PostMapping
    public ResponseEntity<Void> serviceRegister(@RequestBody @Valid RegisterServiceDto serviceDto) {
        serviceOperation.serviceRegister(serviceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> findAllService() {
        List<String> services = serviceOperation.findAllService();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable("id") Long serviceId) {
        Service service = serviceOperation.findById(serviceId);
        return new ResponseEntity<>(service.getName(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ChangeServiceDto changeServiceDto) {
        serviceOperation.update(changeServiceDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
