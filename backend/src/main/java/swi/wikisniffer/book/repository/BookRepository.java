package swi.wikisniffer.book.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import swi.wikisniffer.book.model.searchengine.Book;

public interface BookRepository extends Repository<Book, String> {
    Page<Book> findByTitleInOrTextIn(Collection<String> titles, Collection<String> texts, Pageable pageable);
}
