package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import spring.dto.ChangeExpertDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterExpertDto;
import spring.dto.ResponceExpertDto;
import spring.entity.Expert;
import spring.mapper.Mapper;
import spring.service.ExpertOperation;

import java.io.File;

@RestController
@RequestMapping("/v1/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertOperation expertOperation;

    @PostMapping()
    public ResponseEntity<Void> registerExpert(@ModelAttribute @Valid RegisterExpertDto expertDto) {
        expertOperation.register(expertDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/picture/{expertId}")
    public ResponseEntity<Resource> getExpertPicture(@PathVariable Long expertId) {
        if (expertId == null)
            throw new IllegalArgumentException("expertId can not be Null");
        File imageFile = expertOperation.getPictureFileByUserName(expertId);

        Resource resource = new FileSystemResource(imageFile);

        // Prepare the HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename(imageFile.getName()).build()); // Set content disposition for inline viewing
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponceExpertDto> findById(@PathVariable("id") Long expertId) {
        Expert expert = expertOperation.findById(expertId);
        ResponceExpertDto responceExpertDto = Mapper.ConvertEntityToDto.convertExpertToDto(expert);
        return new ResponseEntity<>(responceExpertDto, HttpStatus.OK);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto passwordDto) {
        expertOperation.changePassword(passwordDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ChangeExpertDto changeExpertDto) {
        expertOperation.update(changeExpertDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findScore/{id}")
    public ResponseEntity<Double>  findScoreById(@PathVariable("id") Long expertId){
        double score = expertOperation.findScoreById(expertId);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

}
