package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.category.dto.CategoryInputDto;
import ru.practicum.category.dto.CategoryOutputDto;
import ru.practicum.category.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryInputDto categoryInputDto);

    CategoryOutputDto toCategoryOutputDto(Category category);

    List<CategoryOutputDto> toCategoryOutputDtoList(List<Category> categories);
}
