package swi.wikisniffer.book.service.impl;

import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.book.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final Searcher searcher;
    private final BookRepository bookRepository;

    public BookServiceImpl(Searcher searcher, BookRepository bookRepository) {
        this.searcher = searcher;
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> getOne(String id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.flatMap(this::parseBookContent);
    }

    @Override
    public List<BookHint> getBookHints(String query, int hintCount) {
        return searcher.getHints(query, hintCount);
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
