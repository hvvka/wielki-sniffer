package swi.wikisniffer.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.service.BookService;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/v1")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/book/{id}")
    public Optional<Book> getFullBook(@PathVariable("id") String id) {
        LOG.info("GET full book id={}", id);
        return bookService.getFullBook(id);
    }
}
