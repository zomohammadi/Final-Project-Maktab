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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {Suggestion.Expert_ID, Suggestion.ORDER_ID}))

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Suggestion extends BaseEntity {
public static final String Expert_ID ="expert_id";
public static final String ORDER_ID ="order_id";
    private Double priceSuggested;

    @Builder.Default
    private ZonedDateTime suggestedDateAndTime = ZonedDateTime.now();

    private ZonedDateTime suggestedTimeStartService;
    private int durationOfService;

    @ManyToOne
    Expert expert;

    @ManyToOne
    Orders order;
}
