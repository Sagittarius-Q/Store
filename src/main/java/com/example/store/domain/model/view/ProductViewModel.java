package com.example.store.domain.model.view;

import lombok.Data;

import java.util.List;
@Data
public class ProductViewModel {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int amount;
}
