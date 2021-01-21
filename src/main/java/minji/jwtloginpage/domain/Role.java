package minji.jwtloginpage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private String value;
}
