package swi.wikisniffer.book.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import swi.wikisniffer.book.model.dto.Chapter;

import java.util.List;
import java.util.stream.Collectors;

public class ChapterMapper {

    public static List<Chapter> mapToResult(List<JsonNode> sectionNodes) {
        return sectionNodes.stream().map(s -> {
            System.out.println(s);
//            String name = s.get("line");
            // TODO: sort by "level" and "index"
            return new Chapter();
        }).collect(Collectors.toList());
    }
}
