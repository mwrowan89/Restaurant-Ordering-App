package com.restaurant.repository;

import com.restaurant.entity.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, String > {
}
