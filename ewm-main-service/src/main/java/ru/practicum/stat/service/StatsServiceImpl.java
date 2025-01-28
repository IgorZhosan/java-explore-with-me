package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.StatDtoInput;
import ru.practicum.StatDtoOutput;
import ru.practicum.stat.client.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private static final String APP_NAME = "ewm-main-service";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsClient statsClient;

    @Override
    public void createStats(String uri, String ip) {
        StatDtoInput statDtoInput = new StatDtoInput();
        statDtoInput.setApp(APP_NAME);
        statDtoInput.setIp(ip);
        statDtoInput.setUri(uri);
        statDtoInput.setTimestamp(LocalDateTime.now());

        StatDtoInput stat = statsClient.createStats(statDtoInput);
        log.info("Информация сохранена {}.", stat);
    }

    @Override
    public List<StatDtoOutput> getStats(List<Long> eventsId, boolean unique) {
        log.info("Запрос на получение статистики для events {}.", eventsId);
        String start = LocalDateTime.now().minusYears(20).format(FORMATTER);
        String end = LocalDateTime.now().plusYears(20).format(FORMATTER);

        String[] uris = eventsId.stream()
                .map(id -> String.format("/events/%d", id))
                .toArray(String[]::new);

        return statsClient.getStats(start, end, uris, unique);
    }

    @Override
    public Map<Long, Long> getView(List<Long> eventsId, boolean unique) {
        log.info("Запрос на получение просмотров для events {}.", eventsId);
        List<StatDtoOutput> stats = getStats(eventsId, unique);
        Map<Long, Long> views = new HashMap<>();
        for (StatDtoOutput stat : stats) {
            Long id = Long.valueOf(stat.getUri().replace("/events/", ""));
            Long view = stat.getHits();
            views.put(id, view);
        }
        log.info("Получены просмотры с сервиса статистики.");
        return views;
    }
}