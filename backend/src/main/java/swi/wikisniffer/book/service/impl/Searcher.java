package swi.wikisniffer.book.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.dto.BookHint;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.BookRepository;
import swi.wikisniffer.book.service.ResultPageMapper;
import swi.wikisniffer.query.service.QuerySplitter;

import static swi.wikisniffer.book.repository.AggregateField.*;

@Service
public class Searcher {

    public static final int MAX_PAGE_SIZE = 100;

    private final QuerySplitter querySplitter;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ResultPageMapper resultPageMapper;

    public Searcher(
        QuerySplitter querySplitter,
        BookRepository bookRepository,
        BookMapper bookMapper,
        ResultPageMapper resultPageMapper
    ) {
        this.querySplitter = querySplitter;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.resultPageMapper = resultPageMapper;
    }

    public List<BookHint> getHints(String query, int hintCount) {
        if (query.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<String> terms = escapeJsonCharacters(querySplitter.split(query));
        Pageable firstHits = PageRequest.of(0, hintCount);
        Page<Book> books = bookRepository.findByTitleInOrTextIn(terms, terms, firstHits);

        return bookMapper.mapToHints(books.getContent());
    }

    public ResultPage getBooks(AdvancedQuery query, int pageNumber, int pageSize) throws ParseException {
        pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        Pageable firstHits = PageRequest.of(pageNumber, pageSize);
        List<AbstractAggregationBuilder<?>> aggregations = new LinkedList<>();
        aggregations.add(getMaxTimestampAggregation());
        aggregations.add(getMinTimestampAggregation());
        aggregations.add(getCategoriesAggregation());
        aggregations.add(getContributorAggregation());
        AggregatedPage<Book> page = bookRepository.getBooks(query, firstHits, aggregations);

        return resultPageMapper.mapToResultPage(page);
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
