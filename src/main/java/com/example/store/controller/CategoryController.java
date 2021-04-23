package com.example.store.controller;

import com.example.store.domain.model.binding.CategoryBindingModel;
import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.domain.model.view.CategoryViewModel;
import com.example.store.domain.model.view.ProductViewModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryController {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getAllCategory(Model model) throws CategoryNotFoundException {
        List<CategoryViewModel> categoryList = this.categoryService.findAllCategories()
                .stream()
                .map(element -> this.modelMapper.map(element, CategoryViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        return "/category/category-list";
    }

    @GetMapping("/add")
    public String getCategoryForm() {
        return "/category/category-form";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute CategoryBindingModel categoryBindingModel, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:add";
        }
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryBindingModel, CategoryServiceModel.class);
        categoryService.save(categoryServiceModel);
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) throws CategoryNotFoundException {
        this.categoryService.delete(id);
        return "redirect:/category/list";
    }

    @GetMapping("/products/{id}")
    public String getProducts(@PathVariable Long id, Model model) throws CategoryNotFoundException {
        List<ProductViewModel> productList = this.categoryService.getProductsInCategoryByCategoryId(id)
                .stream()
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        model.addAttribute("categoryName", " of " + productList.get(0).getCategoryName());
        return "/product/product-list";
    }
}

