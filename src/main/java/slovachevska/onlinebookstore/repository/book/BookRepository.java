package slovachevska.onlinebookstore.repository.book;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import slovachevska.onlinebookstore.model.Book;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book b LEFT JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findAllBooksByCategoryId(Long categoryId, Pageable pageable);
}
