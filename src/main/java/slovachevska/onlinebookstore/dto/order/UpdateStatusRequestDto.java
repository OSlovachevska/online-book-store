package slovachevska.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequestDto {

    @NotNull
    private String status;
}
