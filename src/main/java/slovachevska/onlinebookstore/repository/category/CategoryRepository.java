package slovachevska.onlinebookstore.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
