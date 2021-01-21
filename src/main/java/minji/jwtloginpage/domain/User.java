package minji.jwtloginpage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String userId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column
    private boolean activated;
}
