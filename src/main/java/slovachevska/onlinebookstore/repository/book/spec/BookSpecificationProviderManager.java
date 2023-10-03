package slovachevska.onlinebookstore.repository.book.spec;

import java.util.List;
import lombok.RequiredArgsConstructor;
import slovachevska.onlinebookstore.exception.EntityNotFoundException;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.repository.SpecificationProvider;
import slovachevska.onlinebookstore.repository.SpecificationProviderManager;

@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Can`t find specification provider for key:" + key));
    }

}
