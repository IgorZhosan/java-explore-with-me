package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.StatDtoInput;
import ru.practicum.StatDtoOutput;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatController {

    private final StatService statService;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatDtoInput createStat(@RequestBody @Valid StatDtoInput statDtoInput) {
        // Логируем событие на русском языке
        log.info("Получен запрос на создание статистики: {}", statDtoInput);

        StatDtoInput createdStat = statService.createStat(statDtoInput);

        log.info("Статистика успешно создана: {}", createdStat);
        return createdStat;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatDtoOutput> getStats(
            @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        log.info("Получен запрос на статистику: start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);

        List<StatDtoOutput> stats = statService.getStats(start, end, uris, unique);

        log.info("Возвращаем {} записей статистики", stats.size());
        return stats;
    }
}