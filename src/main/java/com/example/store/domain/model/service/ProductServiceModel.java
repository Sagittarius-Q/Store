package com.example.store.domain.model.service;

import lombok.Data;

import java.util.List;
@Data
public class ProductServiceModel {
    private Long id;
    private String name;
    private List<String> categoriesName;
    private String description;
    private Double price;
    private int amount;
}
