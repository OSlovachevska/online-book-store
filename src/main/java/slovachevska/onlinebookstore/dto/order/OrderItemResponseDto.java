package slovachevska.onlinebookstore.dto.order;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;

    private int quantity;

    private BigDecimal price;
}
