package com.example.store.service.category;

import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.exceptions.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryServiceModel> findAllCategories();
    void save(CategoryServiceModel categoryServiceModel);
    void delete(Long id) throws CategoryNotFoundException;
}
