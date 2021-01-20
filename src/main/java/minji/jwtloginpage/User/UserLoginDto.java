package minji.jwtloginpage.User;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginDto {
    @NotNull
    private String userId;
    @NotNull
    private String userPassword;
}
