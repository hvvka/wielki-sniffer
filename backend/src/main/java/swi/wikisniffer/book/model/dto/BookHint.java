package swi.wikisniffer.book.model.dto;

import java.util.List;

public class BookHint {
    private String id;
    private String title;
    private List<String> categories;
    private String coverImage;

    public String getId() {
        return id;
    }

    public BookHint setId(String id) {
        this.id = id;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookHint setTitle(String title) {
        this.title = title;

        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public BookHint setCategories(List<String> categories) {
        this.categories = categories;

        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public BookHint setCoverImage(String coverImage) {
        this.coverImage = coverImage;

        return this;
    }
}
