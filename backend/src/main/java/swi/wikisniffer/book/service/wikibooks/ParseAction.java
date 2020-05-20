package swi.wikisniffer.book.service.wikibooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Component
class ParseAction {

    private static final Logger LOG = LoggerFactory.getLogger(ParseAction.class);

    @Value("${wikibooks.api.url}")
    private String wikibooksApiUrl;

    private final UnaryOperator<String> apiParseResponse = getApiParseResponse();

    public UnaryOperator<String> getApiParseResponse() {
        return pageId ->
                WebClient.create(wikibooksApiUrl)
                        .mutate()
                        .exchangeStrategies(ExchangeStrategies.builder()
                                .codecs(configurer -> configurer
                                        .defaultCodecs()
                                        .maxInMemorySize(16 * 1024 * 1024))
                                .build())
                        .build()
                        .get()
                        .uri(builder ->
                                builder.queryParam("action", "parse")
                                        .queryParam("format", "json")
                                        .queryParam("pageid", pageId)
                                        .queryParam("prop", "sections|text")
                                        .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }

    public Optional<String> getTextNode(String pageId) {
        String response = apiParseResponse.apply(pageId);
        if (response != null) {
            Function<JsonNode, String> getTextNode = ps -> ps.get("parse").get("text").get("*").textValue();
            return getSelectedNode(pageId, response, getTextNode);
        }
        return Optional.empty();
    }

    public Optional<String> getSectionsNode(String pageId) {
        String response = apiParseResponse.apply(pageId);
        if (response != null) {
            Function<JsonNode, String> getTextNode = ps -> ps.get("parse").get("sections").textValue();
            return getSelectedNode(pageId, response, getTextNode);
        }
        return Optional.empty();
    }

    private Optional<String> getSelectedNode(String pageId, String response, Function<JsonNode, String> getNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode parseResponse = mapper.readTree(response);
            if (parseResponse.get("error") == null) {
                String selectedNode = getNode.apply(parseResponse);
                return Optional.of(selectedNode);
            }
        } catch (JsonProcessingException e) {
            LOG.error("Could not process pageid={}: {}", pageId, response, e);
        }
        return Optional.empty();
    }
}
