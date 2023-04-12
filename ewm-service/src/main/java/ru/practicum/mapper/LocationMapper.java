package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.Location;

@Mapper
public interface LocationMapper {

    LocationDto locationMapToDto(Location location);

    Location locationDtoMapToLocation(LocationDto locationDto);

}
