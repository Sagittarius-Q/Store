package com.example.store.controller;

import com.example.store.domain.model.binding.ProductBindingModel;

import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.domain.model.service.ProductServiceModel;
import com.example.store.domain.model.view.ProductViewModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.exceptions.ProductNotFoundException;
import com.example.store.service.category.CategoryService;
import com.example.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProductController {
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getAllProduct(Model model) {
        List<ProductViewModel> productList = this.productService.getAllProducts()
                .stream()
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        return "/product/product-list";
    }

    @GetMapping("/details/{id}")
    public String getProductById(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductViewModel productViewModel = this.modelMapper.map(this.productService.getProductById(id), ProductViewModel.class);
        model.addAttribute("product", productViewModel);
        return "/product/product-details";
    }

    @GetMapping("/add")
    public String getProductForm(Model model) {
        List<CategoryServiceModel> categoryServiceModel = this.categoryService.findAllCategories();
        List<String> categories = new ArrayList<>();
        for (CategoryServiceModel m : categoryServiceModel) {
            categories.add(m.getName());
        }

        model.addAttribute("product", new ProductBindingModel());
        model.addAttribute("categories", categories);
        return "/product/product-form";
    }

    @PostMapping("/add")
    public String addProductConfirm(@Valid @ModelAttribute ProductBindingModel productBindingModel, BindingResult result) throws CategoryNotFoundException {
        if (result.hasErrors()) {
            return "redirect:add";
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productBindingModel, ProductServiceModel.class);
        productService.save(productServiceModel);
        return "redirect:list";
    }

    @GetMapping("/update/{id}")
    public String getEditForm(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productService.getProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
        List<CategoryServiceModel> categoryServiceModel = this.categoryService.findAllCategories();
        List<String> categories = new ArrayList<>();
        for (CategoryServiceModel m : categoryServiceModel) {
            categories.add(m.getName());
        }
        model.addAttribute("product", productViewModel);
        model.addAttribute("categories", categories);
        return "/product/product-edit-form";
    }

    @PostMapping("/update")
    public String saveUpdatedProduct(ProductViewModel productViewModel) throws ProductNotFoundException, CategoryNotFoundException {
        ProductServiceModel productServiceModel = this.modelMapper.map(productViewModel, ProductServiceModel.class);
        this.productService.updateProduct(productServiceModel);
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/product/list";
    }
}

