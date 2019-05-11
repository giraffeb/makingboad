package com.example.board.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MyKey.class)
public class MyEntity {
    @Id
    private int no;

    @Id
    private int hello;


    @Column
    private String name;

    public MyEntity() {
    }

    public MyEntity(int no, int hello, String name) {
        this.no = no;
        this.hello = hello;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getHello() {
        return hello;
    }

    public void setHello(int hello) {
        this.hello = hello;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
