package ru.practicum.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCategoryDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
