package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.StatDtoInput;
import ru.practicum.model.Stat;

@Mapper
public interface StatMapper {

    StatMapper INSTANCE = Mappers.getMapper(StatMapper.class);

    Stat toStat(StatDtoInput statDtoInput);

    StatDtoInput toStatDtoInput(Stat stat);

}