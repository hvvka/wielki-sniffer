package swi.wikisniffer.book.model.dto;

import java.util.List;

import swi.wikisniffer.book.model.dto.filter.FilterField;
import swi.wikisniffer.book.model.dto.search.SearchField;
import swi.wikisniffer.book.model.dto.sort.SortField;

public class AdvancedQuery {
    private DateRange timestampRange;
    private SortField sortField;
    private List<SearchField> searchFields;
    private List<FilterField> filterFields;

    public DateRange getTimestampRange() {
        return timestampRange;
    }

    public AdvancedQuery setTimestampRange(DateRange timestampRange) {
        this.timestampRange = timestampRange;

        return this;
    }

    public SortField getSortField() {
        return sortField;
    }

    public AdvancedQuery setSortField(SortField sortField) {
        this.sortField = sortField;

        return this;
    }

    public List<SearchField> getSearchFields() {
        return searchFields;
    }

    public AdvancedQuery setSearchFields(List<SearchField> searchFields) {
        this.searchFields = searchFields;

        return this;
    }

    public List<FilterField> getFilterFields() {
        return filterFields;
    }

    public AdvancedQuery setFilterFields(List<FilterField> filterFields) {
        this.filterFields = filterFields;

        return this;
    }
}
