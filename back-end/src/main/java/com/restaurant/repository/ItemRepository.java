package com.restaurant.repository;

import com.restaurant.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, String> {
    List<Item> findAllByOrderId(String orderId);
}
