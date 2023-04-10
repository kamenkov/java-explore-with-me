package ru.practicum.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;
import ru.practicum.service.EventService;

@Mapper(componentModel = "spring")
public abstract class CompilationMapper {

    @Autowired
    protected EventService eventService;

    @Mapping(target = "events", expression = "java( eventService.findByIds(newCompilationDto.getEvents()) )")
    public abstract Compilation newDtoMapToCompilation(NewCompilationDto newCompilationDto);

    @Mapping(target = "events", expression = "java( eventService.findByIds(updateCompilationRequest.getEvents()) )")
    public abstract Compilation updateCompilationRequestMapToCompilation(UpdateCompilationRequest updateCompilationRequest);

    public abstract CompilationDto mapCompilationToDto(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCompilation(Compilation from, @MappingTarget Compilation to);

}

