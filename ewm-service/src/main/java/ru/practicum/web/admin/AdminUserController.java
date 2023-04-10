package ru.practicum.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public AdminUserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }


    @Transactional(readOnly = true)
    @GetMapping
    public List<UserDto> search(@RequestParam(required = false) Set<Long> ids,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        List<User> users = userService.search(ids, from, size);
        return users.stream().map(userMapper::userMapToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        User user = userService.create(userMapper.newUserRequestMapToUser(newUserRequest));
        return userMapper.userMapToDto(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        userService.remove(id);
    }
}
