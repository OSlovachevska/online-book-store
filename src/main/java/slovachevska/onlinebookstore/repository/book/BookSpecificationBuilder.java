package slovachevska.onlinebookstore.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.repository.SpecificationBuilder;
import slovachevska.onlinebookstore.repository.SpecificationProviderManager;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (bookSearchParameters.authors() != null && bookSearchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(bookSearchParameters.authors()));
        }
        if (bookSearchParameters.titles() != null && bookSearchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(bookSearchParameters.titles()));
        }

        return spec;
    }
}
