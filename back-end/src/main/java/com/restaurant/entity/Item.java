 package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int itemId;

    @Column(name = "orderid", nullable = false)
    private String order;

    @Column(name = "itemid", nullable = false)
    private String menuItem;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "notes")
    private String notes;

    @Column(name = "firstname", nullable = false)
    private String firstName;
}