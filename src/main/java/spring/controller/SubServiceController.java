package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.ChangeSubServiceDto;
import spring.dto.RegisterSubServiceDto;
import spring.dto.ResponceSubServiceDto;
import spring.entity.SubService;
import spring.mapper.Mapper;
import spring.service.SubServiceOperation;

import java.util.List;

@RestController
@RequestMapping("/subService")
@RequiredArgsConstructor
public class SubServiceController {
    private final SubServiceOperation subServiceOperation;

    @PostMapping
    public ResponseEntity<Void> subServiceRegister(@RequestBody @Valid RegisterSubServiceDto subServiceDto) {
        subServiceOperation.subServiceRegister(subServiceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponceSubServiceDto>> findAllService() {
        List<ResponceSubServiceDto> subServices = subServiceOperation.findAllSubService();
        return new ResponseEntity<>(subServices, HttpStatus.OK);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ResponceSubServiceDto>> findAllSubServicesOfService(@PathVariable Long serviceId) {
        List<ResponceSubServiceDto> subServices = subServiceOperation.findAllSubServiceOfService(serviceId);
        return new ResponseEntity<>(subServices, HttpStatus.OK);  // Return HTTP 200 with the list of sub-services
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceSubServiceDto> findById(@PathVariable("id") Long subServiceId) {
        SubService subService = subServiceOperation.findById(subServiceId);
        ResponceSubServiceDto responceSubServiceDto = Mapper.ConvertEntityToDto.convertSubServiceToDto(subService);
        return new ResponseEntity<>(responceSubServiceDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ChangeSubServiceDto changeServiceDto) {
        subServiceOperation.update(changeServiceDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
