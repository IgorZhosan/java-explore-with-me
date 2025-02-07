package ru.practicum.compilation.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompilationUpdateDto {

    private List<Long> events;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean pinned = false;

    @Size(min = 1, max = 50)
    private String title;
}
