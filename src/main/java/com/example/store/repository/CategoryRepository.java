package com.example.store.repository;

import com.example.store.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
    Optional<Category> getCategoryByName(String name);
}
