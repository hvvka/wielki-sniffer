package swi.wikisniffer.book.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.query.service.QuerySplitter;

import static swi.wikisniffer.book.repository.AggregateField.*;

@Service
public class Searcher {

    private final QuerySplitter querySplitter;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Value("${search.maxPageSize}")
    private Integer maxPageSize;

    public Searcher(
        QuerySplitter querySplitter,
        BookRepository bookRepository,
        BookMapper bookMapper
    ) {
        this.querySplitter = querySplitter;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Page<Book> getHints(String query, int hintCount) {
        List<String> terms = escapeJsonCharacters(querySplitter.split(query));
        Pageable firstHits = PageRequest.of(0, hintCount);

        return bookRepository.findByTitleInOrTextIn(terms, terms, firstHits);
    }

    public AggregatedPage<Book> getBooks(String query, int pageNumber, int pageSize) {
        pageSize = Math.min(pageSize, maxPageSize);
        List<String> terms = escapeJsonCharacters(querySplitter.split(query));
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        return bookRepository.getBooks(terms, pageRequest, getResultPageAggregations());
    }

    public AggregatedPage<Book> getBooks(AdvancedQuery query, int pageNumber, int pageSize) {
        pageSize = Math.min(pageSize, maxPageSize);
        Pageable firstHits = PageRequest.of(pageNumber, pageSize);

        return bookRepository.getBooks(query, firstHits, getResultPageAggregations());
    }

    private List<AbstractAggregationBuilder<?>> getResultPageAggregations() {
        List<AbstractAggregationBuilder<?>> aggregations = new LinkedList<>();
        aggregations.add(getMaxTimestampAggregation());
        aggregations.add(getMinTimestampAggregation());
        aggregations.add(getCategoriesAggregation());
        aggregations.add(getContributorAggregation());

        return aggregations;
    }

    private MaxAggregationBuilder getMaxTimestampAggregation() {
        return AggregationBuilders.max(TIMESTAMP.getFieldName() + MAX_SUFFIX)
                .field(TIMESTAMP.getAggregatedField());
    }

    private MinAggregationBuilder getMinTimestampAggregation() {
        return AggregationBuilders.min(TIMESTAMP.getFieldName() + MIN_SUFFIX)
                .field(TIMESTAMP.getAggregatedField());
    }

    private TermsAggregationBuilder getCategoriesAggregation() {
        return AggregationBuilders.terms(CATEGORIES.getFieldName())
                .field(CATEGORIES.getAggregatedField());
    }

    private TermsAggregationBuilder getContributorAggregation() {
        return AggregationBuilders.terms(CONTRIBUTOR.getFieldName())
                .field(CONTRIBUTOR.getAggregatedField());
    }

    private List<String> escapeJsonCharacters(List<String> terms) {
        return terms.stream()
                .map(term -> term.replaceAll("(:|\\{|\\}|\\[|\\])", "\\\\$1"))
                .collect(Collectors.toList());
    }
}
