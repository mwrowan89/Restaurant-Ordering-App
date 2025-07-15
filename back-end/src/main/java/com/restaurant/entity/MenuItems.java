package com.restaurant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menuitems")
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
    private String imageUrl;
    @Column(name = "available", nullable = false)
    private Boolean available;

    // Getters and Setters
    public Long getMenuItemId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setMenuItemId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
