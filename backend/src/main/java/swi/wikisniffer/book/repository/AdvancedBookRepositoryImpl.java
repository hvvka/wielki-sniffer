package swi.wikisniffer.book.repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.dto.DateRange;
import swi.wikisniffer.book.model.dto.filter.FilterField;
import swi.wikisniffer.book.model.dto.search.Field;
import swi.wikisniffer.book.model.dto.search.SearchField;
import swi.wikisniffer.book.model.dto.search.SearchTerm;
import swi.wikisniffer.book.model.dto.sort.SortDirection;
import swi.wikisniffer.book.model.dto.sort.SortField;
import swi.wikisniffer.book.model.searchengine.Book;

@Service
public class AdvancedBookRepositoryImpl implements AdvancedBookRepository {
    private final ElasticsearchRestTemplate elasticsearchTemplate;

    public AdvancedBookRepositoryImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public AggregatedPage<Book> getBooks(
            List<String> queryTerms,
            Pageable pageable,
            List<AbstractAggregationBuilder<?>> aggregations
    ) {
        BoolQueryBuilder bookQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder titleQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder textQuery = QueryBuilders.boolQuery();

        queryTerms.forEach(term -> {
            titleQuery.must(getFieldLevelQuery(Field.TITLE, term));
            textQuery.must(getFieldLevelQuery(Field.TEXT, term));
        });

        bookQuery.should(titleQuery);
        bookQuery.should(textQuery);

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder()
                .withQuery(bookQuery)
                .withPageable(pageable);

        aggregations.forEach(query::addAggregation);

        return elasticsearchTemplate.queryForPage(query.build(), Book.class);
    }

    @Override
    public AggregatedPage<Book> getBooks(
            AdvancedQuery advancedQuery,
            Pageable pageable,
            List<AbstractAggregationBuilder<?>> aggregations
    ) {
        BoolQueryBuilder bookQuery = getBookQuery(advancedQuery);

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder()
                .withQuery(bookQuery)
                .withPageable(pageable);

        if (advancedQuery.getSortField() != null) {
            query.withSort(getSort(advancedQuery.getSortField()));
        }

        if (isFilterApplicable(advancedQuery)) {
            QueryBuilder filterQuery = getFilter(advancedQuery);
            query.withFilter(filterQuery);
        }

        aggregations.forEach(query::addAggregation);

        return elasticsearchTemplate.queryForPage(query.build(), Book.class);
    }

    private FieldSortBuilder getSort(SortField sortField) {
        swi.wikisniffer.book.model.dto.sort.Field field = sortField.getField();
        SortOrder order = getSortOrder(sortField.getDirection());

        return SortBuilders.fieldSort(field.getName()).order(order);
    }

    private boolean isFilterApplicable(AdvancedQuery advancedQuery) {
        return advancedQuery.getTimestampRange() != null
                || (advancedQuery.getFilterFields() != null && !advancedQuery.getFilterFields().isEmpty());
    }

    private QueryBuilder getFilter(AdvancedQuery advancedQuery) {
        BoolQueryBuilder filterQuery = QueryBuilders.boolQuery();

        if (advancedQuery.getTimestampRange() != null) {
            filterQuery.must(getTimestampFilter(advancedQuery.getTimestampRange()));
        }

        if (advancedQuery.getFilterFields() != null) {
            getFilterQueries(advancedQuery.getFilterFields()).forEach(filterQuery::must);
        }

        return filterQuery;
    }

    private List<QueryBuilder> getFilterQueries(List<FilterField> filterFields) {
        return filterFields.stream()
                .map(field -> QueryBuilders.termQuery(field.getField().getName(), field.getValue()))
                .collect(Collectors.toList());
    }

    private QueryBuilder getTimestampFilter(DateRange timestampRange) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Book.TIMESTAMP_FORMAT);
        RangeQueryBuilder timestampRangeQuery = QueryBuilders.rangeQuery(swi.wikisniffer.book.model.dto.filter.Field.TIMESTAMP.getName());

        if (timestampRange.getFrom() != null) {
            timestampRangeQuery.gte(dateFormat.format(timestampRange.getFrom()));
        }

        if (timestampRange.getTo() != null) {
            timestampRangeQuery.lte(dateFormat.format(timestampRange.getTo()));
        }

        return timestampRangeQuery;
    }

    private BoolQueryBuilder getBookQuery(AdvancedQuery advancedQuery) {
        BoolQueryBuilder bookMustQuery = QueryBuilders.boolQuery();

        for (SearchField searchField : advancedQuery.getSearchFields()) {
            BoolQueryBuilder fieldShouldQuery = QueryBuilders.boolQuery();

            for (SearchTerm searchTerm : searchField.getShould()) {
                BoolQueryBuilder fieldMustQuery = QueryBuilders.boolQuery();

                for (String value : searchTerm.getMust()) {
                    fieldMustQuery.must(getFieldLevelQuery(searchField.getField(), value));
                }

                if (searchTerm.isNot()) {
                    fieldShouldQuery.mustNot(fieldMustQuery);
                } else {
                    fieldShouldQuery.should(fieldMustQuery);
                }

            }
            bookMustQuery.must(fieldShouldQuery);
        }

        return bookMustQuery;
    }

    private SortOrder getSortOrder(SortDirection direction) {
        return direction == SortDirection.DESC ? SortOrder.DESC : SortOrder.ASC;
    }

    private QueryBuilder getFieldLevelQuery(Field field, String value) {
        switch (field) {
            case CONTRIBUTOR:
            case CATEGORIES:
                return QueryBuilders.termQuery(field.getName(), value);
            default:
                return QueryBuilders.matchPhraseQuery(field.getName(), value);
        }
    }
}
