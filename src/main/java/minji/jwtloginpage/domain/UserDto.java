package minji.jwtloginpage.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotEmpty(message = "id를 입력하세요")
    private String userId;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일을 입력하세요.")
    private String userEmail;

    @NotEmpty(message = "password 입력하세요.")
    private String userPassword;

    @NotNull(message ="필수 입력값입니다.")
    private Role userRole;
}
