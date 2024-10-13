package spring.mapper;

import org.mapstruct.Mapping;
import spring.dto.OrdersBriefDto;
import spring.dto.projection.OrdersBriefProjection;

public interface OrderMapper extends BaseMapper<OrdersBriefProjection, OrdersBriefDto>{
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "subService", target = "subServiceId")
    OrdersBriefDto convertEntityToDTO(OrdersBriefProjection projection);

}
    //List<OrdersBriefDto> convertEntityToDTO(List<OrdersBriefProjection> projectionList);