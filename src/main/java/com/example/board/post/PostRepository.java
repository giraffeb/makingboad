package com.example.board.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PostRepository  extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT count(*) FROM post"
            , nativeQuery = true)
    public int getMaxPageNumber();

    @Query(value="SELECT p.* FROM post p WHERE p.writer_user_no = ?1 ORDER BY p.post_id DESC LIMIT 1", nativeQuery = true)
    public Post getLastPostByUserNo(int user_no);
}
