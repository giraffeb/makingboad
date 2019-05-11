package com.example.board.domain;


import java.io.Serializable;
import java.util.Objects;

public class MyKey implements Serializable {

    private int no;
    private int hello;

    public MyKey() {
    }

    public MyKey(int no, int hello) {
        this.no = no;
        this.hello = hello;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyKey myKey = (MyKey) o;
        return no == myKey.no &&
                hello == myKey.hello;
    }

    @Override
    public int hashCode() {

        return Objects.hash(no, hello);
    }
}
