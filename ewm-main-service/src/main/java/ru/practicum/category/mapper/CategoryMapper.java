package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.category.dto.CategoryInputDto;
import ru.practicum.category.dto.CategoryOutputDto;
import ru.practicum.category.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryInputDto categoryInputDto);

    CategoryOutputDto toCategoryOutputDto(Category category);

    List<CategoryOutputDto> toCategoryOutputDtoList(List<Category> categories);
}
