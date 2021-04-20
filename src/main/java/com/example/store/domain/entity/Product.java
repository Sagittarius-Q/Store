package com.example.store.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @SequenceGenerator(name = "product_seq", allocationSize = 1)
    @GeneratedValue(generator = "product_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
//    @ManyToMany(cascade=CascadeType.ALL)
    private List<Category> categories;
    private String description;
    private Double price;
    private int amount;
}
