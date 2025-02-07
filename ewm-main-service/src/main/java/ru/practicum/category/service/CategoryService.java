package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryInputDto;
import ru.practicum.category.dto.CategoryOutputDto;

import java.util.List;

public interface CategoryService {
    CategoryOutputDto createCategory(CategoryInputDto categoryInputDto);

    CategoryOutputDto updateCategory(long catId, CategoryInputDto categoryInputDto);

    void deleteCategory(long catId);

    CategoryOutputDto getCategoryById(long catId);

    List<CategoryOutputDto> getAllCategories(int from, int size);
}