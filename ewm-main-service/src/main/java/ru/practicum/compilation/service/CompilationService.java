package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDtoInput;
import ru.practicum.compilation.dto.CompilationDtoOutput;
import ru.practicum.compilation.dto.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {

    CompilationDtoOutput createCompilation(CompilationDtoInput compilationRequestDto);

    void deleteCompilation(Long compId);

    CompilationDtoOutput updateCompilation(CompilationUpdateDto compilationUpdateDto, Long compId);

    List<CompilationDtoOutput> getAllCompilations(Boolean pinned, int from, int size);

    CompilationDtoOutput getCompilationById(Long compId);
}
