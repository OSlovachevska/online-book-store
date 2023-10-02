package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.BookDto;
import slovachevska.onlinebookstore.dto.CreateBookRequestDto;
import slovachevska.onlinebookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto book);
}
