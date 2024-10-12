package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.ChangeCustomerDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterCustomerDto;
import spring.dto.ResponceCustomerDto;
import spring.entity.Customer;
import spring.mapper.Mapper;
import spring.service.CustomerOperation;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerOperation customerOperation;

    @PostMapping
    public ResponseEntity<Void> serviceRegister(@RequestBody @Valid RegisterCustomerDto customerDto) {
        customerOperation.register(customerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponceCustomerDto>> findAllService() {
        List<ResponceCustomerDto> customers = customerOperation.findAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceCustomerDto> findById(@PathVariable("id") Long customerId) {
        Customer customer = customerOperation.findById(customerId);
        ResponceCustomerDto responceCustomerDto = Mapper.ConvertEntityToDto.convertCustomerToDto(customer);
        return new ResponseEntity<>(responceCustomerDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ChangeCustomerDto changeCustomerDto) {
        customerOperation.update(changeCustomerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/changepassword")

    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto passwordDto) {
        customerOperation.changePassword(passwordDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
