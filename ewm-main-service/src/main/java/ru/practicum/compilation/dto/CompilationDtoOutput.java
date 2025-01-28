package ru.practicum.compilation.dto;

import lombok.Data;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDtoOutput {

    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
