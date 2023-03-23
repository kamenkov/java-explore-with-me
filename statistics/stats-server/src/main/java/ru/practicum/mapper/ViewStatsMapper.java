package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.ViewStats;

@Mapper
public interface ViewStatsMapper {

    ViewStatsDto mapToViewStatsDto(ViewStats viewStats);

}
