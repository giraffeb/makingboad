package com.example.board.post;


import com.example.board.Users.Users;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Accessors(chain = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private int post_id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_NO", foreignKey = @ForeignKey(name = "users_post_fk_key"))
    private Users writer;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
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



}
