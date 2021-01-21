package minji.jwtloginpage.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserDto {
    @NotEmpty
    private String userId;

    @Email @NotEmpty
    private String userEmail;

    @NotEmpty
    private String userPassword;

    private List<Role> roleList;
}
