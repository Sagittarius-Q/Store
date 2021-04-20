package com.example.store.controller;

import com.example.store.domain.model.binding.CategoryBindingModel;
import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.domain.model.view.CategoryViewModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class CategoryController {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAllCategory(Model model) {
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
        return "redirect:all";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) throws CategoryNotFoundException {
        this.categoryService.delete(id);
        return "redirect:/category/all";
    }
}

