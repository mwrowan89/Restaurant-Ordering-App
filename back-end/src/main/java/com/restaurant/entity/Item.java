 package com.restaurant.entity;

 import jakarta.persistence.*;

 @Entity
 @Table(name = "items")
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

 // Getters and Setters
 public int getItemId() {
 return itemId;
 }

 public void setItemId(int id) {
 this.itemId = id;
 }

 public String getOrder() {
 return order;
 }

 public void setOrder(String order) {
 this.order = order;
 }

 public String getMenuItem() {
 return menuItem;
 }

 public void setMenuItem(String menuItem) {
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