package spring.mapper;

import org.mapstruct.Mapper;
import spring.dto.OrdersBriefDto;
import spring.dto.projection.OrdersBriefProjection;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrdersBriefProjection, OrdersBriefDto>{

    OrdersBriefDto convertEntityToDTO(OrdersBriefProjection projection);

    // Add this method for mapping Instant to ZonedDateTime
    default ZonedDateTime map(Instant value) {
        return value != null ? ZonedDateTime.ofInstant(value, ZoneId.systemDefault()) : null;
    }
}
    //List<OrdersBriefDto> convertEntityToDTO(List<OrdersBriefProjection> projectionList);