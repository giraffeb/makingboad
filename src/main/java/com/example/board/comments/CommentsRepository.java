package com.example.board.comments;

import com.example.board.post.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentsRepository extends CrudRepository<Comments, Integer> {

    List<Comments> findAllByPost(Post post);
}
