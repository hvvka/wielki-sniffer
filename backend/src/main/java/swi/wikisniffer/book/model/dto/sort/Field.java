package swi.wikisniffer.book.model.dto.sort;

public enum Field {
    RELEVANCE("_score"),
    TIMESTAMP("timestamp");

    Field(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
