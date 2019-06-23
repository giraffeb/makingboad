package com.example.board.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {


    Optional<UserType> findByTypeName(String typeName);
}
