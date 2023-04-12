package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;

@Mapper
public interface CompilationMapper {

    @Mapping(target = "events", ignore = true)
    Compilation newDtoMapToCompilation(NewCompilationDto newCompilationDto);

    @Mapping(target = "events", ignore = true)
    Compilation updateCompilationRequestMapToCompilation(UpdateCompilationRequest updateCompilationRequest);

    CompilationDto mapCompilationToDto(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompilation(Compilation from, @MappingTarget Compilation to);

}

