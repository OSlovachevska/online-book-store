package slovachevska.onlinebookstore.mapper;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import slovachevska.onlinebookstore.dto.book.BookResponseDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookResponseDto toDto(Book book);

    Book toModel(CreateBookRequestDto book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget Book book, CreateBookRequestDto bookDto) {
        Set<Category> categories = new HashSet<>();
        for (Long categoryId : bookDto.getCategoryIds()) {
            Category category = new Category();
            category.setId(categoryId);
            categories.add(category);
        }
        book.setCategories(categories);
    }
}
