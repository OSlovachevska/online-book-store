package slovachevska.onlinebookstore.repository.book.spec;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.repository.SpecificationProvider;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("title").in(Arrays.stream(params).toArray());

    }
}
