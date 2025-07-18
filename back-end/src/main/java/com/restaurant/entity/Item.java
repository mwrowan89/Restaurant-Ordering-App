 package com.restaurant.entity;

 import jakarta.persistence.*;

 @Entity
 @Table(name = "items")
 public class Item {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private int itemId;

 // Assuming Order and MenuItem are other entities in your application
// @ManyToOne
// @JoinColumn(name = "orderid", nullable = false)
  @Column(name = "orderid", nullable = false)
  private String orderId;

// @ManyToOne
// @JoinColumn(name = "itemid", nullable = false)
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

 public String getOrderId() {
 return orderId;
 }

 public void setOrderId(String orderId) {
 this.orderId = orderId;
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