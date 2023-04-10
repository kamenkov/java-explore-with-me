package ru.practicum.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationMapper compilationMapper;

    private final CompilationService compilationService;

    public AdminCompilationController(CompilationMapper compilationMapper,
                                      CompilationService compilationService) {
        this.compilationMapper = compilationMapper;
        this.compilationService = compilationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.newDtoMapToCompilation(newCompilationDto);
        compilation = compilationService.create(compilation);
        return compilationMapper.mapCompilationToDto(compilation);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable Long id,
                                 @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationMapper.updateCompilationRequestMapToCompilation(updateCompilationRequest);
        compilation = compilationService.update(id, compilation);
        return compilationMapper.mapCompilationToDto(compilation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        compilationService.remove(id);
    }
}
