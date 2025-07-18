package com.restaurant.repository;

import com.restaurant.entity.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Orders, String > {

    List<Orders> findByUserid(String userid);

}
