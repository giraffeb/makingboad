package com.example.board.comments;


import com.example.board.Users.Users;
import com.example.board.post.Post;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
public class Comments {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int comments_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "POST_ID", foreignKey = @ForeignKey(name = "post_comment_key"))
    private Post post;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_NO", foreignKey = @ForeignKey(name = "comment_user_key"))
    private Users writer;

    @Column(length = 2048)
    private String content;

    @CreationTimestamp
    private Timestamp reg_date;

    @UpdateTimestamp
    private Timestamp mod_date;


    public Comments() {
    }

    public Comments(Post post, Users writer, String content) {
        this.post = post;
        this.writer = writer;
        this.content = content;
    }

    public int getComments_id() {
        return comments_id;
    }

    public void setComments_id(int comments_id) {
        this.comments_id = comments_id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getWriter() {
        return writer;
    }

    public void setWriter(Users writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getReg_date() {
        return reg_date;
    }

    public void setReg_date(Timestamp reg_date) {
        this.reg_date = reg_date;
    }

    public Timestamp getMod_date() {
        return mod_date;
    }

    public void setMod_date(Timestamp mod_date) {
        this.mod_date = mod_date;
    }
}
