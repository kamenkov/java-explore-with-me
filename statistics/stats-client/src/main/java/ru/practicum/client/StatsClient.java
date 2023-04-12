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
import java.util.List;
import java.util.Map;

@Component
public class StatsClient extends BaseClient {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(response.getBody(), new TypeReference<>() {
        });
    }

    public long getViews(Long eventId) {
        List<ViewStatsDto> statsDtos = getStats(
                LocalDateTime.now().minusYears(100L).format(DATE_TIME_FORMATTER),
                LocalDateTime.now().plusYears(100L).format(DATE_TIME_FORMATTER),
                List.of("/events/" + eventId),
                false
        );
        if (statsDtos.isEmpty()) {
            return 0L;
        }
        return statsDtos.get(0).getHits();
    }

}
