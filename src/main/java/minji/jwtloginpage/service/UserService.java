package minji.jwtloginpage.service;

import lombok.RequiredArgsConstructor;
import minji.jwtloginpage.domain.User;
import minji.jwtloginpage.domain.UserDto;
import minji.jwtloginpage.domain.UserLoginDto;
import minji.jwtloginpage.repository.UserJpaRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserDto userDto){
        User newUser= User.builder()
                .userId(userDto.getUserId())
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .activated(false)
                .userRole(userDto.getUserRole())
                .build();
       return userJpaRepo.save(newUser);
    }

    public User login(UserLoginDto userLoginDto) throws Exception {
        User user=userJpaRepo.findByUserId(userLoginDto.getUserId()).orElseThrow(Exception::new);
        if(!passwordEncoder.matches(userLoginDto.getUserPassword(),user.getUserPassword())){
            throw new Exception();
        }

        return user;
    }
}
