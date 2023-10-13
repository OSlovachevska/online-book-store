package slovachevska.onlinebookstore.service.order;

import java.util.Set;
import slovachevska.onlinebookstore.dto.order.OrderItemResponseDto;
import slovachevska.onlinebookstore.dto.order.OrderResponseDto;
import slovachevska.onlinebookstore.dto.order.ShippingAddressRequestDto;
import slovachevska.onlinebookstore.dto.order.UpdateStatusRequestDto;

public interface OrderService {

    OrderResponseDto createOrder(String username,
                                 ShippingAddressRequestDto shippingAddressRequestDto);

    Set<OrderResponseDto> getAllOrdersByUsername(String username);

    OrderResponseDto updateOrderStatus(Long orderid,
                                       UpdateStatusRequestDto updateStatusRequestDto);

    Set<OrderItemResponseDto> getOrderItemsByOrderId(Long orderid);

    OrderItemResponseDto getOrderItemById(Long orderid);

    void doesUserHasThisOrder(String username, Long orderid);
}
