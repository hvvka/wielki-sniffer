package swi.wikisniffer.book.model.dto;

import swi.wikisniffer.book.model.Cover;
import swi.wikisniffer.book.model.searchengine.Book;

import java.util.ArrayList;
import java.util.List;

public class BookResult implements Cover {

    private int id;
    private String title;
    private List<String> categories;
    private String contributor;
    private String timestamp;
    private String coverImage;
    private List<Chapter> contents;

    public BookResult(Book book) {
        this.id = Integer.parseInt(book.getId());
        this.title = book.getTitle();
        this.categories = book.getCategories();
        this.contributor = book.getContributor();
        this.timestamp = book.getTimestamp();
        this.coverImage = book.getFirstImage();
        this.contents = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public BookResult setId(int id) {
        this.id = id;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookResult setTitle(String title) {
        this.title = title;

        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public BookResult setCategories(List<String> categories) {
        this.categories = categories;

        return this;
    }

    public String getContributor() {
        return contributor;
    }

    public BookResult setContributor(String contributor) {
        this.contributor = contributor;

        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BookResult setTimestamp(String timestamp) {
        this.timestamp = timestamp;

        return this;
    }

    @Override
    public String getCoverImage() {
        return coverImage;
    }

    @Override
    public BookResult setCoverImage(String coverImage) {
        this.coverImage = coverImage;

        return this;
    }

    public List<Chapter> getContents() {
        return contents;
    }

    public BookResult setContents(List<Chapter> contents) {
        this.contents = contents;

        return this;
    }
}
