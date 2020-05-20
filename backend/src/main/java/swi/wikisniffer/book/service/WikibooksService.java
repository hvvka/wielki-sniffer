package swi.wikisniffer.book.service;

import swi.wikisniffer.book.model.dto.Chapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WikibooksService {

    Map<String, String> getImagesUrls(List<String> imagesNames);

    Optional<String> getPageText(String pageId);

    List<Chapter> getPageChapters(String pageId);
}
