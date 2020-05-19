package swi.wikisniffer.book.model.dto;

import java.util.List;

public class ResultPage {
    private long resultCount;
    private int pageCount;
    private int pageNumber;
    private List<BookResult> books;
    private List<Facet> categories;
    private List<Facet> contributors;
    private DateRange timestampRange;

    public long getResultCount() {
        return resultCount;
    }

    public ResultPage setResultCount(long resultCount) {
        this.resultCount = resultCount;

        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public ResultPage setPageCount(int pageCount) {
        this.pageCount = pageCount;

        return this;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public ResultPage setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;

        return this;
    }

    public List<BookResult> getBooks() {
        return books;
    }

    public ResultPage setBooks(List<BookResult> books) {
        this.books = books;

        return this;
    }

    public List<Facet> getCategories() {
        return categories;
    }

    public ResultPage setCategories(List<Facet> categories) {
        this.categories = categories;

        return this;
    }

    public List<Facet> getContributors() {
        return contributors;
    }

    public ResultPage setContributors(List<Facet> contributors) {
        this.contributors = contributors;

        return this;
    }

    public DateRange getTimestampRange() {
        return timestampRange;
    }

    public ResultPage setTimestampRange(DateRange timestampRange) {
        this.timestampRange = timestampRange;

        return this;
    }
}
