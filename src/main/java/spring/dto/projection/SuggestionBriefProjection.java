package spring.dto.projection;

import java.time.Instant;

public interface SuggestionBriefProjection {
    Long getId();

    String getDurationOfService();

    Double getPriceSuggested();

    Instant getSuggestedTimeStartService();

    String getUserName();

    String getFirstName();

    String getLastName();

    Integer getScore();
}
