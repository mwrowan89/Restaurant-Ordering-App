 package com.restaurant.entity;

 import jakarta.persistence.*;

 @Entity
 @Table(name = "users")
 public class User {
 @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "ID", nullable = false)
 private Long userId;

 @Column(name = "USERNAME", nullable = false, unique = true)
 private String username;

 @Column(name = "password", nullable = false)
 private String password;

 @Column(name = "FIRST", nullable = false)
 private String firstName;

 @Column(name = "LAST", nullable = false)
 private String lastName;

 @Column(name = "PHONE")
 private String phone;

 @Column(name = "EMAIL", unique = true)
 private String email;

 @Column(name = "IMAGE_URL")
 private String imageUrl;

 @Column(name = "PAN")
 private String pan;

// @Column(name = "credit_card")
// private int creditCardNumber;

 @Column(name = "EXPIRY_MONTH")
 private Integer expiryMonth;

 @Column(name = "EXPIRY_YEAR")
 private Integer expiryYear;

// @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name =
// "user_id"))
 @Column(name = "ROLES")
 private String role;

 // Getters and Setters
 public Long getUSerId() {
 return userId;
 }

 public void setUserId(Long id) {
 this.userId = id;
 }

 public String getUsername() {
 return username;
 }

 public void setUsername(String username) {
 this.username = username;
 }

 public String getPassword() {
 return password;
 }

 public void setPassword(String password) {
 this.password = password;
 }

 public String getFirstName() {
 return firstName;
 }

 public void setFirstName(String firstName) {
 this.firstName = firstName;
 }

 public String getLastName() {
 return lastName;
 }

 public void setLastName(String lastName) {
 this.lastName = lastName;
 }

 public String getPhone() {
 return phone;
 }

 public void setPhone(String phone) {
 this.phone = phone;
 }

 public String getEmail() {
 return email;
 }

 public void setEmail(String email) {
 this.email = email;
 }

 public String getImageUrl() {
 return imageUrl;
 }

 public void setImageUrl(String imageUrl) {
 this.imageUrl = imageUrl;
 }
//
// public int getCreditCardNumber() {
// return creditCardNumber;
// }
//
// public void setCreditCardNumber(int creditCardNumber) {
// this.creditCardNumber = creditCardNumber;
// }

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

 public String getRole() {
 return role;
 }

 public void setRole(String role) {
 this.role = role;
 }
 }