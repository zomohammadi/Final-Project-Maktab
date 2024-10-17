package spring.service;

import org.springframework.transaction.annotation.Transactional;
import spring.dto.FeedbackInfoDto;

public interface FeedbackOperation {
    @Transactional
    void registerFeedback(FeedbackInfoDto feedbackInfoDto);
}
