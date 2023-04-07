package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.repository.CompilationRepository;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class CompilationService {

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    public CompilationService(CompilationRepository compilationRepository,
                              CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.compilationMapper = compilationMapper;
    }

    public Compilation create(Compilation compilation) {
        compilation = compilationRepository.save(compilation);
        return compilation;
    }

    public Compilation update(Long id, Compilation compilation) {
        Compilation savedCompilation = findById(id);
        compilationMapper.updateCompilation(compilation, savedCompilation);
        return compilationRepository.save(compilation);
    }

    private Compilation findById(Long id) {
        return compilationRepository
                .findById(id)
                .orElseThrow(notFoundException("Compilation not found {0}", id));
    }

    public void remove(Long id) {
        Compilation compilation = findById(id);
        compilationRepository.delete(compilation);
    }
}