package spring.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.FeedbackInfoDto;
import spring.entity.Expert;
import spring.entity.Feedback;
import spring.entity.Orders;
import spring.enumaration.OrderStatus;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.repository.FeedbackGateway;
import spring.service.FeedbackOperation;
import spring.service.OrderOperation;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackOperationImpl implements FeedbackOperation {
    private final ExpertGateway expertGateway;
    private final OrderOperation orderOperation;
    private final FeedbackGateway feedbackGateway;

    @Transactional
    @Override
    public void registerFeedback(FeedbackInfoDto feedbackInfoDto) {
        Orders order = orderOperation.findById(feedbackInfoDto.orderId());
        if (order.getOrderStatus() != OrderStatus.Paid)
            throw new IllegalStateException("Order Status must be Paid");
        newScoreOperation(feedbackInfoDto, order);
        Feedback feedback = Mapper.ConvertDtoToEntity
                .convertFeedbackInfoDtoToEntity(feedbackInfoDto.comment(), order);
        feedbackGateway.save(feedback);
    }

    private void newScoreOperation(FeedbackInfoDto feedbackInfoDto, Orders order) {

        Expert expert = order.getExpert();
        int scorePerService = feedbackInfoDto.score() + (-1 * expert.getPerformanceScore());
        int countOfExpertOperationPerSubService = orderOperation.getCountOfExpertOperationPerSubService
                (expert, order.getSubService());
        double currentScore = expert.getScore();
        double newScore = calculateNewScore(currentScore, scorePerService,
                countOfExpertOperationPerSubService);
        if (newScore > 5) newScore = 5;
        expert.setScore(newScore);
        if (newScore < 0)
            expert.setActive(false);
        expertGateway.save(expert);
    }

    private double calculateNewScore(double currentScore, int newRating, int count) {
        double score = (currentScore * (count - 1) + newRating)/count;
        return score;
    }
}
