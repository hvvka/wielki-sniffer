package swi.wikisniffer.book.model.dto.search;

import java.util.List;

public class SearchField {
    private Field field;
    private List<SearchTerm> should;

    public Field getField() {
        return field;
    }

    public SearchField setField(Field field) {
        this.field = field;

        return this;
    }

    public List<SearchTerm> getShould() {
        return should;
    }

    public SearchField setShould(List<SearchTerm> should) {
        this.should = should;

        return this;
    }
}
