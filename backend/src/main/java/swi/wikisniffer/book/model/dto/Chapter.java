package swi.wikisniffer.book.model.dto;

import java.util.List;

public class Chapter {
    private String name;
    private List<Chapter> chapters;

    public String getName() {
        return name;
    }

    public Chapter setName(String name) {
        this.name = name;

        return this;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public Chapter setChapters(List<Chapter> chapters) {
        this.chapters = chapters;

        return this;
    }
}
