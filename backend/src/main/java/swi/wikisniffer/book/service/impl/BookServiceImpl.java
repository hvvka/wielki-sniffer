package swi.wikisniffer.book.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import org.springframework.web.reactive.function.client.WebClient;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.book.service.BookService;
import swi.wikisniffer.book.service.ResultPageMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    private final Searcher searcher;
    private final BookRepository bookRepository;
    private final ResultPageMapper resultPageMapper;
    private final BookMapper bookMapper;

    public BookServiceImpl(Searcher searcher, BookRepository bookRepository, ResultPageMapper resultPageMapper, BookMapper bookMapper) {
        this.searcher = searcher;
        this.bookRepository = bookRepository;
        this.resultPageMapper = resultPageMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public Optional<Book> getOne(String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.flatMap(this::parseBookContent);
    }

    @Override
    public List<BookHint> getBookHints(String query, int hintCount) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>(0);
        }

        return  bookMapper.mapToHints(searcher.getHints(query, hintCount).getContent());
    }

    @Override
    public ResultPage getBooks(String query, int pageNumber, int pageSize) throws ParseException {
        if (query == null || query.isEmpty()) {
            return new ResultPage();
        }

        return resultPageMapper.mapToResultPage(searcher.getBooks(query, pageNumber, pageSize));
    }

    @Override
    public ResultPage getBooks(AdvancedQuery query, int pageNumber, int pageSize) throws ParseException {
        return resultPageMapper.mapToResultPage(searcher.getBooks(query, pageNumber, pageSize));
    }

    private Optional<Book> parseBookContent(Book book) {
        String response = WebClient.create("https://en.wikibooks.org/w/api.php")
                .get()
                .uri(builder ->
                        builder.queryParam("action", "parse")
                                .queryParam("format", "json")
                                .queryParam("page", book.getTitle()) // TODO: concat category (if exists) with the title e.g. {{categories.join('/') + '/' + title}}
                                // TODO: link chapters to query the api for pageid (action=search) and redirect
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj;
        try {
            actualObj = mapper.readTree(response);
            String text = actualObj.get("parse").get("text").get("*").textValue();
            book.setText(text);
        } catch (JsonProcessingException e) {
            LOG.error("", e);
        }

        return Optional.of(book);
    }
}
