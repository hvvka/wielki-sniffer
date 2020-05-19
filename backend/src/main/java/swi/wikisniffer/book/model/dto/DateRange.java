package swi.wikisniffer.book.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import swi.wikisniffer.book.model.searchengine.Book;

public class DateRange {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Book.TIMESTAMP_FORMAT)
    private Date from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Book.TIMESTAMP_FORMAT)
    private Date to;

    public DateRange() {
    }

    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public DateRange setFrom(Date from) {
        this.from = from;

        return this;
    }

    public Date getTo() {
        return to;
    }

    public DateRange setTo(Date to) {
        this.to = to;

        return this;
    }
}
