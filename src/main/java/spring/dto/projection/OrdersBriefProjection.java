package spring.dto.projection;

import spring.entity.SubService;

import java.time.Instant;
import java.time.ZonedDateTime;

public interface OrdersBriefProjection {
    Long getId();
    Long getSubService();
    String getAddress();
    String getUserName();
    Double getPriceSuggested();
    String getServiceDescription();
    Instant getTimeForServiceDone();
    //ZonedDateTime getTimeForServiceDone();
}
