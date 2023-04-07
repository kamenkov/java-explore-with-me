package ru.practicum.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;

@Mapper
public interface CompilationMapper {

    Compilation newDtoMapToCompilation(NewCompilationDto newCompilationDto);

    Compilation updateCompilationRequestMapToCompilation(UpdateCompilationRequest updateCompilationRequest);

    CompilationDto mapCompilationToDto(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompilation(Compilation from, @MappingTarget Compilation to);
}

