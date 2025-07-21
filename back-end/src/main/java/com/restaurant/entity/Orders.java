package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @ManyToOne
    // @JoinColumn
    @Column(name = "userid", nullable = false)
    private String userid;

    @Column(name = "ordertime")
    private LocalDateTime orderTime;
    
    @Column(name = "pickuptime")
    private LocalDateTime pickupTime;

    @Column(name = "area")
    private String area;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "tax")
    private Double tax;
    
    @Column(name = "tip")
    private Double tip;
    
    @Column(name = "pan")
    private String pan;
    
    @Column(name = "expiry_month")
    private Integer expiryMonth;
    
    @Column(name = "expiry_year")
    private Integer expiryYear;
    
    @Column(name = "status")
    private String status;
}