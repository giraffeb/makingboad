package com.example.board.comments;

import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.example.board.post.Post;
import com.example.board.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class CommentsService {


    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PostRepository postRepository;


    public void saveComments(Map<String, Object> params,
                             HttpSession session){

        Users users = usersRepository.findUserByUsername((String) session.getAttribute("username")).get();
        Post post = postRepository.findById(Integer.parseInt((String)params.get("post_id"))).get();

        Comments comments = new Comments(post, users, (String) params.get("comments_content"));

        commentsRepository.save(comments);

    }
}
