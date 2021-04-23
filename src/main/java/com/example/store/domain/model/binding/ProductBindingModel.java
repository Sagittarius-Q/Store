package com.example.store.domain.model.binding;

import lombok.Data;

@Data
public class ProductBindingModel {
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private int amount;
}
