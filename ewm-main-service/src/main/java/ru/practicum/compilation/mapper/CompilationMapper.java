package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.compilation.dto.CompilationDtoInput;
import ru.practicum.compilation.dto.CompilationDtoOutput;
import ru.practicum.compilation.dto.CompilationUpdateDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", source = "evens")
    Compilation toCompilation(CompilationDtoInput compilationRequestDto,
                              List<Event> evens);

    @Mapping(target = "events", source = "eventShortDtoList")
    CompilationDtoOutput toCompilationDto(Compilation compilation,
                                          List<EventShortDto> eventShortDtoList);

    @Mapping(target = "events", ignore = true)
    Compilation toCompilationFromUpdate(CompilationUpdateDto dto);

    List<CompilationDtoOutput> toCompilationDtoList(List<Compilation> compilations);
}
