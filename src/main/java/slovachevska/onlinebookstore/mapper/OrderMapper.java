package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.order.OrderResponseDto;
import slovachevska.onlinebookstore.model.Order;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);
}
