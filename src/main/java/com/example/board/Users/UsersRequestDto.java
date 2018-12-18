package com.example.board.Users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UsersRequestDto {

    @NotBlank(message = "아이디를 지정해주세요.")
    @Pattern(regexp = "\\w{4,15}", message = "아이디 패턴이 올바르지 않습니다.")
    //{4, 15} <- 이정도가 됩니다.
    private String username;

    @NotBlank(message = "비밀번호를 지정해주세요.")
    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,15}$", message = "비밀번호 패턴이 올바르지 않습니다.")
    //{8,15} <- 이정도가 됩니다.
    private String password;

    public UsersRequestDto() {
    }

    public UsersRequestDto(@NotBlank(message = "아이디를 지정해주세요.") @Pattern(regexp = "\\w{4,15}") String username, @NotBlank(message = "비밀번호를 지정해주세요.") @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users toEntity(){
        return new Users(this.username, this.password);
    }
}
