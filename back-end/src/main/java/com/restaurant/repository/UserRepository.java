package com.restaurant.repository;

import com.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//TODO update methods to jpa naming convention
//TODO comment all methods
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRole(String role);
    User getUserByUsername(String username);
    
}
