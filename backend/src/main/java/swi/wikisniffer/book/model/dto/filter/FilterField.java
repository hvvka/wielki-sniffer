package swi.wikisniffer.book.model.dto.filter;

public class FilterField {
    private Field field;
    private String value;

    public Field getField() {
        return field;
    }

    public FilterField setField(Field field) {
        this.field = field;

        return this;
    }

    public String getValue() {
        return value;
    }

    public FilterField setValue(String value) {
        this.value = value;

        return this;
    }
}
