package ru.practicum.dto.category;

import javax.validation.constraints.NotBlank;

public class NewCategoryDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
