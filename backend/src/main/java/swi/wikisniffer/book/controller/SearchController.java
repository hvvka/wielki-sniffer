package swi.wikisniffer.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.service.BookService;

import java.util.List;

@RestController
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/v1/search")
public class SearchController {

    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    private final BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/hint", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookHint> getHints(
            @RequestParam(defaultValue = "${request.defaultQuery}") String query,
            @RequestParam(defaultValue = "${request.defaultHintCount}") int hintCount
    ) {
        LOG.info("GET {} hints for query '{}'", hintCount, query);
        return bookService.getBookHints(query, hintCount);
    }
}
