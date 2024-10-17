package spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.FeedbackInfoDto;
import spring.service.FeedbackOperation;

@RestController
@RequestMapping("/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackOperation feedbackOperation;
    @PostMapping()
    public ResponseEntity<Void> registerFeedback(@RequestBody @Valid FeedbackInfoDto feedbackInfoDto){
        feedbackOperation.registerFeedback(feedbackInfoDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
