package ru.practicum.web.publics;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public PublicCategoryController(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> search(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                    @RequestParam(defaultValue = "10") @Positive int size) {
        return categoryService.search(from, size).stream()
                .map(categoryMapper::categoryMapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return categoryMapper.categoryMapToDto(category);
    }

}
