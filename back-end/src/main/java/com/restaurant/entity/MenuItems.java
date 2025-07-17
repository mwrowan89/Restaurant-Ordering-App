package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menuitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1500)
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(name = "available", nullable = false)
    private Boolean available;
}
