// package com.restaurant.entity;

// import jakarta.persistence.*;

// @Entity
// public class Item {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private int itemId;

// // Assuming Order and MenuItem are other entities in your application
// @ManyToOne
// @JoinColumn(name = "order_id", nullable = false)
// private Orders order;

// @ManyToOne
// @JoinColumn(name = "menu_item_id", nullable = false)
// private MenuItems menuItem;

// @Column(nullable = false)
// private double price;

// @Column
// private String notes;

// @Column(nullable = false)
// private String firstName;

// // Getters and Setters
// public int getItemId() {
// return itemId;
// }

// public void setItemId(int id) {
// this.itemId = id;
// }

// public Orders getOrder() {
// return order;
// }

// public void setOrder(Orders order) {
// this.order = order;
// }

// public MenuItems getMenuItem() {
// return menuItem;
// }

// public void setMenuItem(MenuItems menuItem) {
// this.menuItem = menuItem;
// }

// public double getPrice() {
// return price;
// }

// public void setPrice(double price) {
// this.price = price;
// }

// public String getNotes() {
// return notes;
// }

// public void setNotes(String notes) {
// this.notes = notes;
// }

// public String getFirstName() {
// return firstName;
// }

// public void setFirstName(String firstName) {
// this.firstName = firstName;
// }
// }