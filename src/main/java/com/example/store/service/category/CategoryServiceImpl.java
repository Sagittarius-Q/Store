package com.example.store.service.category;

import com.example.store.domain.entity.Category;
import com.example.store.domain.model.service.CategoryServiceModel;
import com.example.store.exceptions.CategoryNotFoundException;
import com.example.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void delete(Long id) throws CategoryNotFoundException {
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public void save(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                  .stream()
                  .map(element -> this.modelMapper.map(element ,CategoryServiceModel.class))
                  .collect(Collectors.toList());
    }
}
