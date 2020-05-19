package swi.wikisniffer.book.model.dto.sort;

public class SortField {
    private Field field;
    private SortDirection direction;

    public Field getField() {
        return field;
    }

    public SortField setField(Field field) {
        this.field = field;

        return this;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public SortField setDirection(SortDirection direction) {
        this.direction = direction;

        return this;
    }
}
