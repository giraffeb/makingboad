package com.example.board.Users;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class UsersRequestDto {

    @NotBlank(message = "아이디를 지정해주세요.")
    @Pattern(regexp = "\\w{4,15}", message = "아이디 패턴이 올바르지 않습니다.")
    //{4, 15} <- 이정도가 됩니다.
    private String userId;

    @NotBlank(message = "유저이름을 지정해주세요.")
    @Pattern(regexp = "\\w{4,15}", message = "유저이름 패턴이 올바르지 않습니다.")
    //{4, 15} <- 이정도가 됩니다.
    private String username;

    @NotBlank(message = "비밀번호를 지정해주세요.")
    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,15}$", message = "비밀번호 패턴이 올바르지 않습니다.")
    //{8,15} <- 이정도가 됩니다.
    private String password;

    public Users toEntity(){
        return new Users().setUserId(userId).setUsername(username).setPassword(password);
    }
}
