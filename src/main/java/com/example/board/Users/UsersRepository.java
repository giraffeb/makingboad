package com.example.board.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

//    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Optional<Users> findByUserId(String userid);
    Optional<Users> findUserByUsername(String username);
    Integer deleteUsersByUsername(String username);

}
