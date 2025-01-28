package ru.practicum.service;

import ru.practicum.StatDtoInput;
import ru.practicum.StatDtoOutput;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    StatDtoInput createStat(StatDtoInput statDtoInput);

    List<StatDtoOutput> getStats(LocalDateTime start,
                                 LocalDateTime end,
                                 List<String> uris,
                                 Boolean unique);
}