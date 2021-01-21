package minji.jwtloginpage.service;

import lombok.RequiredArgsConstructor;
import minji.jwtloginpage.domain.User;
import minji.jwtloginpage.domain.UserLoginDto;
import minji.jwtloginpage.repository.UserJpaRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;

    public User signUp(User user){
        User newUser= User.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userPassword(passwordEncoder.encode(user.getUserPassword()))
                .activated(true)
                .roleList(new ArrayList<>(user.getRoleList()))
                .build();
       return userJpaRepo.save(newUser);
    }

    public User login(UserLoginDto userLoginDto){
        return null;
    }
}
