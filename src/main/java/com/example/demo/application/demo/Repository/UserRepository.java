package com.example.demo.application.demo.Repository;


import com.example.demo.application.demo.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>{
     Optional<User> findByUsername(String username);
}
