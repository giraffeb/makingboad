//package com.example.board.domain;
//
//import com.example.board.Users.Users;
//
//import javax.persistence.*;
//
////@Entity
//public class UserRoles {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int UserRoleId;
//
//
//    @ManyToOne
//    @JoinColumn(referencedColumnName = "USER_NO", foreignKey = @ForeignKey(name = "users_roles_fk_key"))
//    private Users users;
//
//    @Column
//    private String role;
//
//    public UserRoles() {
//    }
//
//    public UserRoles(Users users, String role) {
//        this.users = users;
//        this.role = role;
//    }
//
//    public int getUserRoleId() {
//        return UserRoleId;
//    }
//
//    public void setUserRoleId(int userRoleId) {
//        UserRoleId = userRoleId;
//    }
//
//    public Users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Users users) {
//        this.users = users;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//}
