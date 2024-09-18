package entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Suggestion extends BaseEntity {

    private Double priceSuggested;

    @Builder.Default
    private ZonedDateTime suggestedDateAndTime = ZonedDateTime.now();

    private ZonedDateTime suggestedTimeStartWork;
    String durationOfWork;

}
