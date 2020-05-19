package swi.wikisniffer.book.service.wikibooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(WikibooksClient.class);

    @Value("${wikibooks.api.url}")
    private String wikibooksApiUrl;

    private WebClient client;

    @Override
    public Map<String, String> getImagesUrls(List<String> imagesNames) {
        return extractImagesUrls(getImagesUrlsResponse(imagesNames));
    }

    @Override
    public Optional<String> getPageContent(String title) {
        String response = WebClient.create(wikibooksApiUrl)
                .get()
                .uri(builder ->
                        builder.queryParam("action", "parse")
                                .queryParam("format", "json")
                                .queryParam("page", title)
                                // TODO: link chapters to query the api for pageid (action=search) and redirect
                                .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode parseResponse = mapper.readTree(response);
                if (parseResponse.get("error") == null) {
                    return Optional.of(parseResponse.get("parse").get("text").get("*").textValue());
                } else {
                    LOG.error("Wikibooks API could not find title {}: {}", title, response);
                }
            } catch (JsonProcessingException e) {
                LOG.error("", e);
            }
        }
        return Optional.empty();
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
