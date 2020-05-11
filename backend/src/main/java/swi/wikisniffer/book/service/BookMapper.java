package swi.wikisniffer.book.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.service.wikibooks.WikibooksClient;

@Service
public class BookMapper {
    private final WikibooksClient wikibooksClient;

    public BookMapper(WikibooksClient wikibooksClient) {
        this.wikibooksClient = wikibooksClient;
    }

    public List<BookHint> mapToHints(List<Book> books) {
        List<BookHint> hints = books.stream()
                .map(this::mapToHint)
                .collect(Collectors.toList());
        fillImageUrls(hints);

        return hints;
    }

    private BookHint mapToHint(Book book) {
        BookHint hint = new BookHint();
        hint.setId(book.getId())
            .setTitle(book.getTitle())
            .setCategories(book.getCategories())
            .setCoverImage(book.getFirstImage());

        return hint;
    }

    private void fillImageUrls(List<BookHint> hints) {
        List<String> imagesNames = hints.stream()
                .map(BookHint::getCoverImage)
                .collect(Collectors.toList());
        imagesNames.removeAll(Collections.singleton(null));
        Map<String, String> imagesUrls = wikibooksClient.getImagesUrls(imagesNames);

        hints.forEach(hint -> {
            if (hint.getCoverImage() != null && imagesUrls.containsKey(hint.getCoverImage())) {
                hint.setCoverImage(imagesUrls.get(hint.getCoverImage()));
            }
        });
    }
}
