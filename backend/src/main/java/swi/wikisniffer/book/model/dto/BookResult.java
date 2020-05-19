package swi.wikisniffer.book.model.dto;

import java.util.List;

import swi.wikisniffer.book.model.Cover;

public class BookResult implements Cover {
    private int id;
    private String title;
    private List<String> categories;
    private String contributor;
    private String timestamp;
    private String coverImage;
    private List<Chapter> contents;

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
