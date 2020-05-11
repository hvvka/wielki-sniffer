package swi.wikisniffer.book.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.service.Searcher;

@RestController
@RequestMapping("/v1/search")
public class SearchController {

    private final Searcher searcher;

    public SearchController(Searcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping(value = "/hint", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookHint> getHints(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "3") int hintCount
    ) {
        return searcher.getHints(query, hintCount);
    }
}
