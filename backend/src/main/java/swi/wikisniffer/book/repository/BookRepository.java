package swi.wikisniffer.book.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import swi.wikisniffer.book.model.searchengine.Book;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String>, AdvancedBookRepository {

    Page<Book> findByTitleInOrTextIn(Collection<String> titles, Collection<String> texts, Pageable pageable);
}
