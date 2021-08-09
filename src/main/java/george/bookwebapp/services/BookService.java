package george.bookwebapp.services;

import george.bookwebapp.models.Book;
import george.bookwebapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    public Book getById(Long id) {
        return this.repository.getById(id);
    }

    public List<Book> findAllActiveBooks() { return this.repository.findAllByDeletedIsNull(); }

    public List<Book> findAllIncativeBooks() { return this.repository.findAllByDeletedIsNotNull(); }

    public void save(Book book) {
        this.repository.save(book);
    }

    public void delete(Long id) {
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss:SSS");
        Book book = this.repository.getById(id);
        book.setDeleted(new Date());
        this.repository.save(book);
    }

}
