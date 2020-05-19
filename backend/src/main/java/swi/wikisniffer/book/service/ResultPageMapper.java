package swi.wikisniffer.book.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.stereotype.Service;
import swi.wikisniffer.book.model.dto.DateRange;
import swi.wikisniffer.book.model.dto.Facet;
import swi.wikisniffer.book.model.dto.ResultPage;
import swi.wikisniffer.book.model.searchengine.Book;
import swi.wikisniffer.book.repository.AggregateField;
import swi.wikisniffer.book.service.impl.BookMapper;

import static swi.wikisniffer.book.repository.AggregateField.*;

@Service
public class ResultPageMapper {
    private static final String MAX_DATE = "Infinity";
    private static final String MIN_DATE = "-Infinity";

    private final BookMapper bookMapper;

    public ResultPageMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public ResultPage mapToResultPage(AggregatedPage<Book> aggregatedPage) throws ParseException {
        ResultPage resultPage = new ResultPage();
        resultPage.setBooks(bookMapper.mapToResult(aggregatedPage.getContent()))
                .setPageCount(aggregatedPage.getTotalPages())
                .setResultCount(aggregatedPage.getTotalElements())
                .setContributors(getFacets(CONTRIBUTOR.getFieldName(), aggregatedPage))
                .setCategories(getFacets(CATEGORIES.getFieldName(), aggregatedPage))
                .setTimestampRange(getDateRange(aggregatedPage, TIMESTAMP, Book.TIMESTAMP_FORMAT))
                .setPageNumber(aggregatedPage.getNumber());

        return resultPage;
    }

    private <T> List<Facet> getFacets(String fieldName, AggregatedPage<T> page) {
        Terms aggregation = page.getAggregations().get(fieldName);
        Map<String, Long> counts = new HashMap<>();

        for (Terms.Bucket bucket : aggregation.getBuckets()) {
            String key = bucket.getKeyAsString();
            Long currentCount = counts.get(key);
            currentCount = currentCount != null ? currentCount : 0;

            counts.put(bucket.getKeyAsString(), bucket.getDocCount() + currentCount);
        }

        return counts.entrySet().stream()
                .map(count -> new Facet(count.getKey(), count.getValue()))
                .collect(Collectors.toList());
    }

    private <T> DateRange getDateRange(AggregatedPage<T> page, AggregateField field, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Min min = page.getAggregations().get(field.getAggregatedField() + MIN_SUFFIX);
        Max max = page.getAggregations().get(field.getAggregatedField() + MAX_SUFFIX);
        Date minDate = isUnboundedDate(min.getValueAsString()) ? null : dateFormat.parse(min.getValueAsString());
        Date maxDate = isUnboundedDate(max.getValueAsString()) ? null : dateFormat.parse(max.getValueAsString());

        return new DateRange(minDate, maxDate);
    }

    private boolean isUnboundedDate(String date) {
        return  date.equals(MIN_DATE) || date.equals(MAX_DATE);
    }
}
