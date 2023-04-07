package ru.practicum.web.publics;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
public class PublicCompilationController {


    @Transactional(readOnly = true)
    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "false") boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable Long id) {
        return null;
    }

}
