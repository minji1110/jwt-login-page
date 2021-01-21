package minji.jwtloginpage.controller;

import lombok.RequiredArgsConstructor;
import minji.jwtloginpage.domain.User;
import minji.jwtloginpage.domain.UserDto;
import minji.jwtloginpage.domain.UserLoginDto;
import minji.jwtloginpage.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public User signUp(@ModelAttribute @Valid UserDto userDto){
        User newUser=userService.signUp(userDto);
        return newUser;
    }

    @PostMapping("/login")
    public User login(@ModelAttribute @Valid UserLoginDto userLoginDto) throws Exception {
        return userService.login(userLoginDto);
    }
}
