package com.example.board.Users;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "typeNo")
    private int typeNo;

    @Column(name= "typeName",nullable = false, unique = true)
    private String typeName;

}
