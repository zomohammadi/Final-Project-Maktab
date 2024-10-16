package spring.dto.projection;

import java.time.Instant;

@SuppressWarnings("unused")
public interface SuggestionBriefProjection {
    Long getSuggestionId();

    int getDurationOfService();

    Double getPriceSuggested();

    Instant getSuggestedTimeStartService();

    String getUserName();

    String getFirstName();

    String getLastName();

    Integer getScore();
}
