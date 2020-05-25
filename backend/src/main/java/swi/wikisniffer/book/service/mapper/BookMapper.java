package swi.wikisniffer.book.service.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.Cover;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.BookResult;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.service.wikibooks.WikibooksClient;

import java.util.*;
import java.util.stream.Collectors;

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

    public BookResult mapToResult(Book book) {
        BookResult result = new BookResult();
        String text = getBookResultText(book);
        return result.setCategories(book.getCategories())
                .setContents(new ArrayList<>())
                .setContributor(book.getContributor())
                .setCoverImage(book.getFirstImage())
                .setId(Integer.parseInt(book.getId()))
                .setTimestamp(book.getTimestamp())
                .setTitle(book.getTitle())
                .setText(text);
    }

    private String getBookResultText(Book book) {
        String text;
        Optional<String> pageText = wikibooksClient.getPageText(book.getId());
        if (pageText.isPresent()) {
            Document doc = Jsoup.parseBodyFragment(pageText.get());
            doc.select("img,a").forEach(Node::remove);
            text = doc.text();
        } else {
            text = book.getText();
        }
        text = text.length() > 400 ? text.substring(0, 400).concat("...") : text;
        return text;
    }

    public List<BookResult> mapToResult(List<Book> books) {
        List<BookResult> results = books.stream()
                .map(this::mapToResult)
                .collect(Collectors.toList());
        fillImageUrls(results);

        return results;
    }

    private BookHint mapToHint(Book book) {
        BookHint hint = new BookHint();
        hint.setId(book.getId())
                .setTitle(book.getTitle())
                .setCategories(book.getCategories())
                .setCoverImage(book.getFirstImage());

        return hint;
    }

    private void fillImageUrls(List<? extends Cover> coverImages) {
        List<String> imagesNames = coverImages.stream()
                .map(Cover::getCoverImage)
                .collect(Collectors.toList());
        imagesNames.removeAll(Collections.singleton(""));
        Map<String, String> imagesUrls = wikibooksClient.getImagesUrls(imagesNames);

        coverImages.forEach(cover -> {
            if (cover.getCoverImage() != null && imagesUrls.containsKey(cover.getCoverImage())) {
                cover.setCoverImage(imagesUrls.get(cover.getCoverImage()));
            }
        });
    }
}
