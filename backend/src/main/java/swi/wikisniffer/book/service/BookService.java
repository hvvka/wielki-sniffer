package swi.wikisniffer.book.service;

import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.searchengine.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getOne(String id);

    List<BookHint> getBookHints(String query, int hintCount);
}
