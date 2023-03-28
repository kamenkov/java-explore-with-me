package ru.practicum.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;

public class StatsClient extends BaseClient {

    public StatsClient(String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void addHit(EndpointHitDto endPointHitDto) {
        post("/hit", endPointHitDto);
    }
}
