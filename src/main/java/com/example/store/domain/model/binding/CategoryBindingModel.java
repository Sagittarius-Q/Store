package com.example.store.domain.model.binding;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CategoryBindingModel {
    @Size(min = 4, max = 25, message
            = "Category name must be between 2 and 25 characters")
    private String name;
}
