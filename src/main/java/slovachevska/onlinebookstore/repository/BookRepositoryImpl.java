package slovachevska.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import slovachevska.onlinebookstore.exception.EntityNotFoundException;
import slovachevska.onlinebookstore.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can`t create a book : " + book);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> getAllBooks = session.createQuery("from Book", Book.class);
            return getAllBooks.getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t find all books");
        }
    }

    @Override
    public Optional<Book> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Book book = session.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t get book by id:" + id);
        }
    }
}
