 package com.restaurant.entity;

 import jakarta.persistence.*;

 @Entity
 @Table(name = "items")
 public class Item {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private int itemId;

 @ManyToOne
 @JoinColumn(name = "orderid", nullable = false)
 private Orders order;

 @ManyToOne
 @JoinColumn(name = "itemid", nullable = false)
 private MenuItems menuItem;

 @Column(name = "price", nullable = false)
 private double price;

 @Column(name = "notes")
 private String notes;

 @Column(name = "firstname", nullable = false)
 private String firstName;

 // Getters and Setters
 public int getItemId() {
 return itemId;
 }

 public void setItemId(int id) {
 this.itemId = id;
 }

 public Orders getOrder() {
 return order;
 }

 public void setOrder(Orders order) {
 this.order = order;
 }

 public MenuItems getMenuItem() {
 return menuItem;
 }

 public void setMenuItem(MenuItems menuItem) {
 this.menuItem = menuItem;
 }

 public double getPrice() {
 return price;
 }

 public void setPrice(double price) {
 this.price = price;
 }

 public String getNotes() {
 return notes;
 }

 public void setNotes(String notes) {
 this.notes = notes;
 }

 public String getFirstName() {
 return firstName;
 }

 public void setFirstName(String firstName) {
 this.firstName = firstName;
 }
 }