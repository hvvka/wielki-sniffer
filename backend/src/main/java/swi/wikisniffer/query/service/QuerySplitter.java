package swi.wikisniffer.query.service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class QuerySplitter {
    public List<String> split(String query) {
        List<String> terms = new LinkedList<>();
        Matcher termMatcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(query);

        while (termMatcher.find()) {
            terms.add(termMatcher.group(1).replace("\"", ""));
        }

        return terms;
    }
}
