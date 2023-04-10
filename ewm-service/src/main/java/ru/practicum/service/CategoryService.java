package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoryRepository;

import java.util.List;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category savedCategory = findById(id);
        categoryMapper.updateCategory(category, savedCategory);
        return categoryRepository.save(savedCategory);
    }

    public List<Category> search(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).getContent();
    }

    public Category findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(notFoundException("Category not found {0}", id));
    }

    public void remove(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
