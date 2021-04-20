package com.example.store.domain.model.binding;

import com.example.store.domain.model.view.CategoryViewModel;
import lombok.Data;

import java.util.List;


@Data
public class ProductBindingModel {
    private String name;
    private List<String> categoriesName;
    private String description;
    private Double price;
    private int amount;
}
