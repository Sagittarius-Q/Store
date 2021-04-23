package com.example.store.service.product;

import com.example.store.domain.model.service.ProductServiceModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    void save(ProductServiceModel productServiceModel) throws CategoryNotFoundException;
    void deleteProductById(Long id);
    List<ProductServiceModel> getAllProducts();
    ProductServiceModel getProductById(Long id) throws ProductNotFoundException;
    void updateProduct(ProductServiceModel productServiceModel) throws ProductNotFoundException, CategoryNotFoundException;
}
