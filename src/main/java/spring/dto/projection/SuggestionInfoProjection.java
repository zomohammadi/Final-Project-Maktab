package spring.dto.projection;

import java.time.ZonedDateTime;

public interface SuggestionInfoProjection {
    int getDurationOfService();
    ZonedDateTime getSuggestedTimeStartService();
    Double getPriceSuggested();
}
