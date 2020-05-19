package swi.wikisniffer.book.model.dto.filter;

public enum Field {
    CATEGORIES("categories.keyword"),
    CONTRIBUTOR("contributor_name.keyword"),
    TIMESTAMP("timestamp");

    private String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
