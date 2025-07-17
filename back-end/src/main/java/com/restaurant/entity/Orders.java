 package com.restaurant.entity;

 import jakarta.persistence.*;

 import java.time.LocalDateTime;

 @Entity
 @Table(name = "orders")
 public class Orders {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column (name = "id")
 private Long orderId;

// @ManyToOne
// @JoinColumn
 @Column (name = "userid", nullable = false)
 private String userid;

 @Column (name = "ordertime")
 private LocalDateTime orderTime;
 @Column (name = "pickuptime")
 private LocalDateTime pickupTime;

 @Column (name = "area")
 private String area;
 @Column (name = "location")
 private String location;
 @Column (name = "tax")
 private Double tax;
 @Column (name = "tip")
 private Double tip;
 @Column (name = "pan")
 private String pan;
 @Column (name = "expiry_month")
 private Integer expiryMonth;
 @Column (name = "expiry_year")
 private Integer expiryYear;
 @Column (name = "status")
 private String status;

 // Getters and Setters
 public Long getId() {
 return orderId;
 }

 public void setId(Long id) {
 this.orderId = id;
 }

 public String getUserid() {
 return userid;
 }

 public void setUserid(String user) {
 this.userid = user;
 }

 public LocalDateTime getOrderTime() {
 return orderTime;
 }

 public void setOrderTime(LocalDateTime orderTime) {
 this.orderTime = orderTime;
 }

 public LocalDateTime getPickupTime() {
 return pickupTime;
 }

 public void setPickupTime(LocalDateTime pickupTime) {
 this.pickupTime = pickupTime;
 }

 public String getArea() {
 return area;
 }

 public void setArea(String area) {
 this.area = area;
 }

 public String getLocation() {
 return location;
 }

 public void setLocation(String location) {
 this.location = location;
 }

 public Double getTax() {
 return tax;
 }

 public void setTax(Double tax) {
 this.tax = tax;
 }

 public Double getTip() {
 return tip;
 }

 public void setTip(Double tip) {
 this.tip = tip;
 }

 public String getPan() {
 return pan;
 }

 public void setPan(String pan) {
 this.pan = pan;
 }

 public Integer getExpiryMonth() {
 return expiryMonth;
 }

 public void setExpiryMonth(Integer expiryMonth) {
 this.expiryMonth = expiryMonth;
 }

 public Integer getExpiryYear() {
 return expiryYear;
 }

 public void setExpiryYear(Integer expiryYear) {
 this.expiryYear = expiryYear;
 }

 public String getStatus() {
 return status;
 }

 public void setStatus(String status) {
 this.status = status;
 }
 }