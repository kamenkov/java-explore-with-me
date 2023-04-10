package ru.practicum.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.dto.event.*;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.service.CategoryService;

@Mapper(componentModel = "spring")
public abstract class EventMapper {

    @Autowired
    protected CategoryService categoryService;

    @Mapping(target = "category", qualifiedByName = "mapCategoryById")
    public abstract Event newEventDtoMapToEvent(NewEventDto newEventDto);

    public abstract EventFullDto eventMapToFullDto(Event event);

    public abstract EventShortDto eventMapToShortDto(Event event);

    @Mapping(target = "category", qualifiedByName = "mapCategoryById")
    public abstract Event updateEventUserRequestMapToEvent(UpdateEventUserRequest eventUserRequest);

    @Mapping(target = "category", qualifiedByName = "mapCategoryById")
    public abstract Event updateEventAdminRequestMapToEvent(UpdateEventAdminRequest eventAdminRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "state", target = "state", ignore = true)
    public abstract void updateEvent(Event from, @MappingTarget Event to);

    @Named("mapCategoryById")
    protected Category mapCategoryById(Long categoryId) {
        return categoryId != null ? categoryService.findById(categoryId) : null;
    }

}
