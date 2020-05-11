package swi.wikisniffer.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.query.service.QuerySplitter;

@Service
public class Searcher {
    private final QuerySplitter querySplitter;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Searcher(QuerySplitter querySplitter, BookRepository bookRepository, BookMapper bookMapper) {
        this.querySplitter = querySplitter;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookHint> getHints(String query, int hintCount) {
        if (query.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<String> terms = querySplitter.split(query);
        Pageable firstHits = PageRequest.of(0, hintCount);
        Page<Book> books = bookRepository.findByTitleInOrTextIn(terms, terms, firstHits);

        return bookMapper.mapToHints(books.getContent());
    }
}
