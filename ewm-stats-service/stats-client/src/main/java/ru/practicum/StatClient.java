package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class StatClient extends BaseClient {

    @Autowired
    public StatClient(@Value("${stats-service.url}") String serverUrl,
                      RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createStat(StatDtoInput statDtoInput) {
        return post("/hit", statDtoInput);
    }

    public ResponseEntity<Object> getStats(String start,
                                           String end,
                                           @Nullable List<String> uris,
                                           boolean unique) {
        Map<String, Object> parameters;

        if (Objects.isNull(uris)) {
            parameters = Map.of("start", URLEncoder.encode(start, StandardCharsets.UTF_8),
                    "end", URLEncoder.encode(end, StandardCharsets.UTF_8),
                    "unique", unique);
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        }

        parameters = Map.of("start", URLEncoder.encode(start, StandardCharsets.UTF_8),
                "end", URLEncoder.encode(end, StandardCharsets.UTF_8),
                "uris", String.join(",", uris),
                "unique", unique);
        return get("/stats?start={start}&end={end}&unique={unique}&uris={uris}", parameters);
    }
}