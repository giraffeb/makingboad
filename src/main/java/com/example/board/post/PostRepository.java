package com.example.board.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository  extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT count(*) FROM post"
            , nativeQuery = true)
    public int getMaxPageNumber();

}
