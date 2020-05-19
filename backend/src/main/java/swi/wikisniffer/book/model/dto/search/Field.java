package swi.wikisniffer.book.model.dto.search;

public enum Field {
    TITLE("title"),
    TEXT("text"),
    CONTRIBUTOR("contributor_name"),
    CATEGORIES("categories");

    private String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
