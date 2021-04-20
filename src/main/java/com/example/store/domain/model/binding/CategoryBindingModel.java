package com.example.store.domain.model.binding;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CategoryBindingModel {
    @Size(min = 4, max = 10, message
            = "Category name must be between 4 and 10 characters")
    private String name;
}
