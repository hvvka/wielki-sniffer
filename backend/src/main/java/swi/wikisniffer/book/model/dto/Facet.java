package swi.wikisniffer.book.model.dto;

public class Facet {
    private String name;
    private long count;

    public Facet() {
    }

    public Facet(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Facet setName(String name) {
        this.name = name;

        return this;
    }

    public long getCount() {
        return count;
    }

    public Facet setCount(int count) {
        this.count = count;

        return this;
    }
}
