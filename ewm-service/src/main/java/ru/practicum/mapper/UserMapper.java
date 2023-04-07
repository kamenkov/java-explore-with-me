package ru.practicum.mapper;

import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.User;

public interface UserMapper {

    UserShortDto userMapToShortDto(User user);
}
