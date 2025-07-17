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
    private String imageurl;
    @Column(name = "available", nullable = false)
    private Boolean available;
    
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference(value = "item-menuItem")
    private java.util.List<Item> items;

    // Getters and Setters
    public Long getId() {
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

    public String getImageurl() {
        return imageurl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setId(Long id) {
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

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    // Added for customer profile

    public java.util.List<Item> getItems() {
        return items;
    }
    
    public void setItems(java.util.List<Item> items) {
        this.items = items;
    }
}
