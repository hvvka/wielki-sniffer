package swi.wikisniffer.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/v1")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/book/{id}")
    public Optional<Book> getBook(@PathVariable("id") String id) {
        LOG.info("GET book id={}", id);
        Optional<Book> book = bookRepository.findById(id);
        return book.flatMap(this::parseBookContent);
    }

    private Optional<Book> parseBookContent(Book book) {
        String image = book.getFirstImage()
                .replaceAll("\\s\\.", ".")
                .replaceAll("\\s", "_");
        String imageTag = "Image : " + book.getFirstImage();
        String parsedText = book.getText()
                .replaceAll("\\.\\s", ".")
                .replaceAll(imageTag,
                        String.format("<img src=https://upload.wikimedia.org/wikipedia/commons/a/aa/%s alt=\"%s\">", image, image)
                );
        book.setText(parsedText);
        return Optional.of(book);
    }
}
