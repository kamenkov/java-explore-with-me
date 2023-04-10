package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.event.ParticipationRequestDto;
import ru.practicum.model.Request;

@Mapper
public interface RequestMapper {

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto requestMapToParticipationRequestDto(Request request);
}
