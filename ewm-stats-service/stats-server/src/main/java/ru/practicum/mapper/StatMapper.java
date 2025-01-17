package ru.practicum.mapper;

import ru.practicum.StatDtoInput;
import ru.practicum.model.Stat;

public class StatMapper {

    public static Stat toStat(StatDtoInput statDtoInput) {

        final Stat stat = new Stat();

        stat.setIp(statDtoInput.getIp());
        stat.setUri(statDtoInput.getUri());
        stat.setTimestamp(statDtoInput.getTimestamp());
        stat.setApp(statDtoInput.getApp());

        return stat;
    }

    public static StatDtoInput toStatDto(Stat stat) {

        final StatDtoInput statDtoInput = new StatDtoInput();

        statDtoInput.setIp(stat.getIp());
        statDtoInput.setUri(stat.getUri());
        statDtoInput.setTimestamp(stat.getTimestamp());
        statDtoInput.setApp(stat.getApp());

        return statDtoInput;
    }

}
