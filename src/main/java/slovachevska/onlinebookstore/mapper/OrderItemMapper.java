package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.order.OrderItemResponseDto;
import slovachevska.onlinebookstore.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    OrderItemResponseDto toDto(OrderItem orderItem);
}
