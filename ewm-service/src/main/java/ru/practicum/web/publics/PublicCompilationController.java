package ru.practicum.web.publics;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    public PublicCompilationController(CompilationService compilationService, CompilationMapper compilationMapper) {
        this.compilationService = compilationService;
        this.compilationMapper = compilationMapper;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        List<Compilation> compilations = compilationService.publicSearch(pinned, from, size);
        return compilations.stream()
                .map(compilationMapper::mapCompilationToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable Long id) {
        Compilation compilation = compilationService.findById(id);
        return compilationMapper.mapCompilationToDto(compilation);
    }

}
