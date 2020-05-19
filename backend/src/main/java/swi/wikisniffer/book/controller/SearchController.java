package swi.wikisniffer.book.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.service.BookService;

@RestController
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/v1/search")
public class SearchController {

    private final BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/hint", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookHint> getHints(
            @RequestParam(defaultValue = "${request.defaultQuery}") String query,
            @RequestParam(defaultValue = "${request.defaultHintCount}") int hintCount
    ) {
        return bookService.getBookHints(query, hintCount);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultPage search(
            @RequestParam(defaultValue = "${request.defaultQuery}") String query,
            @RequestParam(defaultValue = "${request.defaultPageNumber}") int pageNumber,
            @RequestParam(defaultValue = "${request.defaultPageSize}") int pageSize
    ) throws ParseException {
        return bookService.getBooks(query, pageNumber, pageSize);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultPage search(
            @RequestBody AdvancedQuery query,
            @RequestParam(defaultValue = "${request.defaultPageNumber}") int pageNumber,
            @RequestParam(defaultValue = "${request.defaultPageSize}") int pageSize
    ) throws ParseException {
        return bookService.getBooks(query, pageNumber, pageSize);
    }
}
