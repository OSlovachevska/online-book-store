package slovachevska.onlinebookstore.repository;

import org.springframework.data.jpa.domain.Specification;
import slovachevska.onlinebookstore.repository.book.BookSearchParameters;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters bookSearchParameters);
}
