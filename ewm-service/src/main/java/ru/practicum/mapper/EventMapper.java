package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.event.*;
import ru.practicum.model.Event;

@Mapper
public interface EventMapper {

    @Mapping(target = "category", ignore = true)
    Event newEventDtoMapToEvent(NewEventDto newEventDto);

    EventFullDto eventMapToFullDto(Event event);

    EventShortDto eventMapToShortDto(Event event);

    @Mapping(target = "category", ignore = true)
    Event updateEventUserRequestMapToEvent(UpdateEventUserRequest eventUserRequest);

    @Mapping(target = "category", ignore = true)
    Event updateEventAdminRequestMapToEvent(UpdateEventAdminRequest eventAdminRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "state", target = "state", ignore = true)
    void updateEvent(Event from, @MappingTarget Event to);

    @Condition
    default boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
