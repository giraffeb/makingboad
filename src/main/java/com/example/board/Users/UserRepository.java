package com.example.board.Users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Integer> {

//    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Optional<Users> findUserByUsername(String username);
}
