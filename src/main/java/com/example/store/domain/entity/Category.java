package com.example.store.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @SequenceGenerator(name = "category_seq", allocationSize = 1)
    @GeneratedValue(generator = "category_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
