package ru.practicum.web.publics;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    @Transactional(readOnly = true)
    @GetMapping
    public List<CategoryDto> search(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return null;
    }

}
