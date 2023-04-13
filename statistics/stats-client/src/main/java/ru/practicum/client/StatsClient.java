package ru.practicum.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatsClient extends BaseClient {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StatsClient(@Value("${stats_server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void addHit(EndpointHitDto endPointHitDto) {
        post("/hit", endPointHitDto);
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        Map<String, Object> params = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        ResponseEntity<Object> response = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", params);
        return objectMapper.convertValue(response.getBody(), new TypeReference<>() {
        });
    }

    public Map<Long, Long> getViewsForEvents(List<Long> eventIds, LocalDateTime startDate) {
        if (startDate == null) {
            return Collections.emptyMap();
        }
        List<String> uris = eventIds.stream().map(id -> "/events/" + id).collect(Collectors.toList());
        List<ViewStatsDto> statsDtos = getStats(startDate.format(DATE_TIME_FORMATTER),
                LocalDateTime.now().format(DATE_TIME_FORMATTER),
                uris,
                false);
        if (statsDtos.isEmpty()) {
            return Collections.emptyMap();
        }
        return statsDtos.stream()
                .collect(Collectors.toMap(dto -> Long.parseLong(dto.getUri().substring(8)),
                        ViewStatsDto::getHits));
    }

}
