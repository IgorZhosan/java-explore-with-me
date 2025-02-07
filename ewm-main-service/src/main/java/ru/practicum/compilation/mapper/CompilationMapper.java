package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.compilation.dto.CompilationDtoInput;
import ru.practicum.compilation.dto.CompilationDtoOutput;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    Compilation toCompilation(CompilationDtoInput dto, List<Event> events);

    CompilationDtoOutput toCompilationDto(Compilation compilation, List<EventShortDto> eventShortDtoList);
}