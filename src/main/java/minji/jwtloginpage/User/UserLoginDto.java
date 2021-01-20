package minji.jwtloginpage.User;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginDto {
    @NotEmpty(message = "id를 입력하세요.")
    private String userId;
    @NotEmpty(message = "password를 입력하세요.")
    private String userPassword;
}
