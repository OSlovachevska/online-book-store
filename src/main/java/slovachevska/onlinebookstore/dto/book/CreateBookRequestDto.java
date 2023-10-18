package slovachevska.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateBookRequestDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @NotNull
    @Size(min = 1, max = 255)
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;

    @NotNull
    private Set<Long> categoryIds;
}


