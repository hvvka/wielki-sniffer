package swi.wikisniffer.book.model.dto.search;

import java.util.List;

public class SearchTerm {
    private List<String> must;
    private boolean not;

    public List<String> getMust() {
        return must;
    }

    public SearchTerm setMust(List<String> must) {
        this.must = must;

        return this;
    }

    public boolean isNot() {
        return not;
    }

    public SearchTerm setNot(boolean not) {
        this.not = not;

        return this;
    }
}
