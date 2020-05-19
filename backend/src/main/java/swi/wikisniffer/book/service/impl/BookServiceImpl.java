package swi.wikisniffer.book.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.book.service.BookService;
import swi.wikisniffer.book.service.ResultPageMapper;

@Service
public class BookServiceImpl implements BookService {

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

    // FIXME please
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
