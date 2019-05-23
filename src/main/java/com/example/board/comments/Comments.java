package com.example.board.comments;


import com.example.board.Users.Users;
import com.example.board.post.Post;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
@Data
@Accessors(chain = true)
public class Comments {

    @Id
    @Column(name = "COMMENTS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int comments_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(referencedColumnName = "POST_ID", foreignKey = @ForeignKey(name = "post_comment_key"))
    private Post post;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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

}
