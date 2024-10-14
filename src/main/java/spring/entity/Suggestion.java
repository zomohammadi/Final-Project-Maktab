package spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity

@SuperBuilder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"expert_id", "order_id"}))

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Suggestion extends BaseEntity {

    private Double priceSuggested;

    @Builder.Default
    private ZonedDateTime suggestedDateAndTime = ZonedDateTime.now();

    private ZonedDateTime suggestedTimeStartService;
    private ZonedDateTime durationOfService;

    @ManyToOne
    Expert expert;

    @ManyToOne
    Orders order;
}
