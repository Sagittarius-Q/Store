package com.example.store.controller;

import com.example.store.domain.model.binding.ProductBindingModel;

import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.domain.model.service.ProductServiceModel;
import com.example.store.domain.model.view.CategoryViewModel;
import com.example.store.domain.model.view.ProductViewModel;
import com.example.store.exceptions.ProductNotFoundException;
import com.example.store.service.category.CategoryService;
import com.example.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAllProduct(Model model) {
        List<ProductViewModel> productList = this.productService.getAllProducts()
                .stream()
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        return "/product/product-list";
    }

    @GetMapping
    public String getProductById() {
        return null;
    }

    @GetMapping("/add")
    public String getProductForm(Model model) {
        List<CategoryServiceModel> categoryServiceModel = this.categoryService.findAllCategories();
        List<CategoryViewModel> categoryViewModels = categoryServiceModel
                .stream()
                .map(element -> this.modelMapper.map(element, CategoryViewModel.class))
                .collect(Collectors.toList());
        List<String> categories = new ArrayList<>();
        for(CategoryServiceModel m : categoryServiceModel) {
            categories.add(m.getName());
        }

        model.addAttribute("product", new ProductBindingModel());
        model.addAttribute("categories", categories);
        return "/product/product-form";
    }

    @PostMapping("/add")
    public String addProductConfirm(@ModelAttribute ProductBindingModel productBindingModel) {
        ProductServiceModel productServiceModel = this.modelMapper.map(productBindingModel, ProductServiceModel.class);
        productService.save(productServiceModel);
        return "redirect:all";
    }

    @GetMapping("/update/{id}")
    public String getEditForm(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productService.getProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
        model.addAttribute("product", productViewModel);
        return "/product/product-edit-form";
    }

    @PostMapping("/update")
    public String saveUpdatedProduct(ProductViewModel productViewModel) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);
        this.productService.updateProduct(productServiceModel);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return  "redirect:/product/all";
    }
}

