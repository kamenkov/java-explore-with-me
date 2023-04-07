package ru.practicum.web.admin;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {


    @Transactional(readOnly = true)
    @GetMapping
    public List<UserDto> search(@RequestParam Set<Long> ids,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        return Collections.emptyList();
    }

    @PostMapping
    public UserDto create(@RequestBody NewUserRequest newUserRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {

    }
}
