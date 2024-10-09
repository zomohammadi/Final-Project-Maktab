package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.RegisterServiceDto;
import spring.service.ServiceOperation;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceOperation serviceOperation;

    @PostMapping
    public void serviceRegister(@RequestBody @Valid RegisterServiceDto serviceDto) {
        serviceOperation.serviceRegister(serviceDto);

    }

}
