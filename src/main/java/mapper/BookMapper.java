package mapper;

import dto.CreateBookRequestDto;
import dto.BookDto;
import model.Book;
import org.mapstruct.Mapper;
import config.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookDto toDto (Book book);

    Book toModel(CreateBookRequestDto book);
}
