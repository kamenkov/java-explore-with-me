package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;

import java.util.List;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventService eventService;

    private final CompilationMapper compilationMapper;

    public CompilationService(CompilationRepository compilationRepository,
                              EventService eventService,
                              CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.eventService = eventService;
        this.compilationMapper = compilationMapper;
    }

    public Compilation create(Compilation compilation, List<Long> eventIds) {
        List<Event> events = eventService.findByIds(eventIds);
        compilation.setEvents(events);
        compilation = compilationRepository.save(compilation);
        return compilation;
    }

    public Compilation update(Long id, Compilation compilation, List<Long> eventIds) {
        Compilation savedCompilation = findById(id);
        compilationMapper.updateCompilation(compilation, savedCompilation);
        List<Event> events = eventService.findByIds(eventIds);
        savedCompilation.setEvents(events);
        return compilationRepository.save(savedCompilation);
    }

    public Compilation findById(Long id) {
        return compilationRepository
                .findById(id)
                .orElseThrow(notFoundException("Compilation not found {0}", id));
    }

    public void remove(Long id) {
        Compilation compilation = findById(id);
        compilationRepository.delete(compilation);
    }

    @Transactional(readOnly = true)
    public List<Compilation> publicSearch(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return compilationRepository.findByPinned(pinned, pageable).getContent();
    }
}
