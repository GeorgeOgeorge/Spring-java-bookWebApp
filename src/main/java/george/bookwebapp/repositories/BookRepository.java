package george.bookwebapp.repositories;

import george.bookwebapp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    //not deleted books
    public List<Book> findAllByDeletedIsNull();
    //deleted books
    public List<Book> findAllByDeletedIsNotNull();
}
