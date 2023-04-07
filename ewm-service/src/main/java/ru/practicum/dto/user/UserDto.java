package ru.practicum.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserDto {

    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
