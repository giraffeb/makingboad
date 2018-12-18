package com.example.board.post;


import com.example.board.Users.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int post_id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_NO", foreignKey = @ForeignKey(name = "users_post_fk_key"))
    private Users writer;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int viewCount;

    @CreationTimestamp
    private Timestamp registeredDate;

    @UpdateTimestamp
    private Timestamp modifiedDate;

    //Constructor
    public Post() {
    }

    public Post(Users writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }



    //setter, getter
    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Users getWriter() {
        return writer;
    }

    public void setWriter(Users writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Timestamp getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Timestamp registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
