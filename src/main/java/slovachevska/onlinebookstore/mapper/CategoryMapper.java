package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.category.CategoryRequestDto;
import slovachevska.onlinebookstore.dto.category.CategoryResponseDto;
import slovachevska.onlinebookstore.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryRequestDto);
}
