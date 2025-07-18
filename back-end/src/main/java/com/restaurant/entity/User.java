package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "ROLES")
    private String role;

    // For Auth purposes
    @Column(name = "activated", nullable = false)
    private boolean activated = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Authority> authorities;


}