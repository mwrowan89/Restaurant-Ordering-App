package com.restaurant.repository;

import com.restaurant.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByRole(String role);
}
