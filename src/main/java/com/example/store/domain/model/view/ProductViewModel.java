package com.example.store.domain.model.view;

import lombok.Data;

@Data
public class ProductViewModel {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private int amount;
}
