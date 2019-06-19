package com.example.board.Users;


import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Accessors(chain = true)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_NO")
    private int userNo;

    @ManyToOne
    @JoinColumn(referencedColumnName= "typeNo",foreignKey = @ForeignKey(name = "usertype_user_fk_key"))
    private UserType userType;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column
    private String password;

    @CreationTimestamp
    private Timestamp registeredDate;

    @Column(columnDefinition="BIT(1) default 1")
    private boolean isEnabled;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
