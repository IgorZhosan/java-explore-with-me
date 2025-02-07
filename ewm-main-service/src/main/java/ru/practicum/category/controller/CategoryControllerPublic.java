package ru.practicum.category.controller;

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
import ru.practicum.category.dto.CategoryOutputDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryOutputDto getCategoryById(@PathVariable @Positive long catId) {
        return categoryService.getCategoryById(catId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryOutputDto> getAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                    @RequestParam(defaultValue = "10") @Positive int size) {
        return categoryService.getAllCategories(from, size);
    }
}