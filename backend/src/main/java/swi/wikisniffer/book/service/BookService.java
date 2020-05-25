package swi.wikisniffer.book.service;

import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.model.searchengine.Book;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getFullBook(String id);
    
    List<BookHint> getBookHints(String query, int hintCount);

    ResultPage getBooks(String query, int pageNumber, int pageSize) throws ParseException;

    ResultPage getBooks(AdvancedQuery query, int pageNumber, int pageSize) throws ParseException;
}
