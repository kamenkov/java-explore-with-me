package ru.practicum.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.model.Category;

@Mapper
public interface CategoryMapper {

    Category dtoMapToCategory(CategoryDto categoryDto);

    Category newDtoMatToCategory(NewCategoryDto newCategoryDto);

    CategoryDto categoryMapToDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(Category from, @MappingTarget Category to);
}
