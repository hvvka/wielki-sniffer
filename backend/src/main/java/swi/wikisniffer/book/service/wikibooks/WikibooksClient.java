package swi.wikisniffer.book.service.wikibooks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import swi.wikisniffer.book.service.WikibooksService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WikibooksClient implements WikibooksService {

    @Value("${wikibooks.api.url}")
    private String wikibooksApiUrl;

    private final ParseAction parseAction;

    private WebClient client;

    public WikibooksClient(ParseAction parseAction) {
        this.parseAction = parseAction;
    }

    @Override
    public Map<String, String> getImagesUrls(List<String> imagesNames) {
        return extractImagesUrls(getImagesUrlsResponse(imagesNames));
    }

    @Override
    public Optional<String> getPageText(String pageId) {
        return parseAction.getTextNode(pageId);
    }

    @Override
    public Optional<String> getPageSections(String pageId) {
        return parseAction.getSectionsNode(pageId);
    }

    private Map<String, String> extractImagesUrls(String apiResponse) {
        Map<String, String> imageUrls = new HashMap<>();

        JSONObject response = new JSONObject(apiResponse);

        if (!response.has(Api.Query.Response.QUERY)) {
            return imageUrls;
        }

        JSONObject query = response.getJSONObject(Api.Query.Response.QUERY);

        if (!query.has(Api.Query.Response.ResponseQuery.PAGES)) {
            return imageUrls;
        }

        JSONObject pages = query.getJSONObject(Api.Query.Response.ResponseQuery.PAGES);

        for (int i = 1; pages.has("-" + i); i++) {
            JSONObject page = pages.getJSONObject("-" + i);

            if (page.has(Api.Query.Response.ResponseQuery.ResponsePage.TITLE)) {
                String title = page.getString(Api.Query.Response.ResponseQuery.ResponsePage.TITLE)
                        .replaceFirst("File:", "");

                if (page.has(Api.Query.Response.ResponseQuery.ResponsePage.IMAGE_INFO)) {
                    JSONArray imageInfos = page.getJSONArray(Api.Query.Response.ResponseQuery.ResponsePage.IMAGE_INFO);

                    if (imageInfos.length() > 0) {
                        JSONObject info = imageInfos.getJSONObject(0);

                        if (info.has(Api.Query.ImageInfo.Response.URL)) {
                            String url = info.getString(Api.Query.ImageInfo.Response.URL);
                            imageUrls.put(title, url);
                            String fileName = url.substring(url.lastIndexOf("/") + 1);
                            imageUrls.put(fileName, info.getString(Api.Query.ImageInfo.Response.URL));
                        }
                    }
                }
            }
        }

        return imageUrls;
    }

    private String getImagesUrlsResponse(List<String> imagesNames) {
        if (imagesNames.isEmpty()) {
            return "{}";
        }

        String reducedNames = imagesNames.stream().reduce("", (acc, name) -> acc + "File:" + name + "|");
        final String titles = reducedNames.substring(0, reducedNames.length() - 1);

        return getClient()
                .get()
                .uri(builder ->
                        builder.queryParam(Api.ACTION, Api.Query.ACTION)
                                .queryParam(Api.Query.TITLES, titles)
                                .queryParam(Api.Query.PROP, Api.Query.ImageInfo.PROP)
                                .queryParam(Api.Query.ImageInfo.II_PROP, Api.Query.ImageInfo.IIProp.URL)
                                .queryParam(Api.FORMAT, Api.Format.JSON)
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private WebClient getClient() {
        if (client == null) {
            client = WebClient.create(wikibooksApiUrl);
        }
        return client;
    }

}
