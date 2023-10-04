package slovachevska.onlinebookstore.repository;

import org.springframework.data.jpa.domain.Specification;
import slovachevska.onlinebookstore.model.Book;

public interface SpecificationProvider<T> {

    String getKey();

    Specification<Book> getSpecification(String[] params);
}
