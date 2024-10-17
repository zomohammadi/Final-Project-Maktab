package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Feedback;

public interface FeedbackGateway extends JpaRepository<Feedback, Long> {
}
