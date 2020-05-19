package swi.wikisniffer.book.repository;

import java.util.List;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import swi.wikisniffer.book.model.dto.AdvancedQuery;
import swi.wikisniffer.book.model.searchengine.Book;

public interface AdvancedBookRepository {
    AggregatedPage<Book> getBooks(AdvancedQuery advancedQuery,
                                  Pageable pageable,
                                  List<AbstractAggregationBuilder<?>> aggregations);
}
