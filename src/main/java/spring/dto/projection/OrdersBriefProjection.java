package spring.dto.projection;

import java.time.Instant;
@SuppressWarnings("unused")
public interface OrdersBriefProjection {
    Long getOrderId();

    String getSubServiceName();

    String getAddress();

    String getUserName();

    Double getPriceSuggested();

    String getServiceDescription();

    Instant getTimeForServiceDone();
}
