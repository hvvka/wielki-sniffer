package swi.wikisniffer.book.repository;

public enum AggregateField {
    CATEGORIES("categories", "categories.keyword"),
    CONTRIBUTOR("contributor_name", "contributor_name.keyword"),
    TIMESTAMP("timestamp", "timestamp");

    public static final String MAX_SUFFIX = "_max";
    public static final String MIN_SUFFIX = "_min";

    private String fieldName;
    private String aggregatedField;

    AggregateField(String fieldName, String facetField) {
        this.fieldName = fieldName;
        this.aggregatedField = facetField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAggregatedField() {
        return aggregatedField;
    }
}
