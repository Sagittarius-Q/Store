package com.example.store.service.product;

import com.example.store.domain.entity.Category;
import com.example.store.domain.entity.Product;
import com.example.store.domain.model.service.ProductServiceModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.exceptions.ProductNotFoundException;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public void save(ProductServiceModel productServiceModel) throws CategoryNotFoundException {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Category category;
        if (productServiceModel.getCategoryName() != null) {
            category = this.categoryRepository.getCategoryByName(productServiceModel.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException("category not found"));
        } else {
            category = this.categoryRepository.getCategoryByName("default")
                    .orElseThrow(() -> new CategoryNotFoundException("Default category not found"));
        }

        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductServiceModel> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return products
                .stream()
                .map(element -> this.modelMapper.map(element, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel getProductById(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public void updateProduct(ProductServiceModel model) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = this.productRepository
                .findById(model.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (model.getCategoryName() != null) {
            Category category = this.categoryRepository.getCategoryByName(model.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException("category not found"));
            product.setCategory(category);
        } else {
            product.setCategory(this.categoryRepository.getCategoryByName("default").orElseThrow(() -> new CategoryNotFoundException("Default category not found")));
        }
        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setAmount(model.getAmount());
        product.setPrice(model.getPrice());

        this.productRepository.save(product);
    }

}
