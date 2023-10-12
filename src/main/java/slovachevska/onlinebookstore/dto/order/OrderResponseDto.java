package slovachevska.onlinebookstore.dto.order;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;

    private Long userId;

    private Double total;

    private String status;

    private LocalDateTime orderDate;

    private String shippingAddress;

    private Set<OrderItemResponseDto> orderItems;

}
