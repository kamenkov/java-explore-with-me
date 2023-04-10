package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.User;

@Mapper
public interface UserMapper {

    UserShortDto userMapToShortDto(User user);

    UserDto userMapToDto(User user);

    User newUserRequestMapToUser(NewUserRequest newUserRequest);

}
