package com.restaurant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menuitems")
public class MenuItem {
    @Id
    @Column(name = "id")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String category;

    @Column(nullable = false)
    private double price;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(nullable = false)
    private Boolean available;

    //Getters and Setters
    public String getId() {
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

    public void setId(String id) {
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

    public void setImageurl(String imageUrl) {
        this.imageurl = imageUrl;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
