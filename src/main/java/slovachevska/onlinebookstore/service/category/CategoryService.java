package slovachevska.onlinebookstore.service.category;

import java.util.List;
import org.springframework.data.domain.Pageable;
import slovachevska.onlinebookstore.dto.category.CategoryRequestDto;
import slovachevska.onlinebookstore.dto.category.CategoryResponseDto;

public interface CategoryService {

    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
