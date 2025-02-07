package ru.practicum.compilation.contoller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilation.dto.CompilationDtoOutput;
import ru.practicum.compilation.service.CompilationServiceImpl;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationServiceImpl compilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDtoOutput> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                         @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                         @RequestParam(defaultValue = "10") @Positive int size) {
        return compilationService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDtoOutput getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }
}