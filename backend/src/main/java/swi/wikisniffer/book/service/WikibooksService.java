package swi.wikisniffer.book.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WikibooksService {

    Map<String, String> getImagesUrls(List<String> imagesNames);

    Optional<String> getPageText(String pageId);

    Optional<String> getPageSections(String pageId);

}
