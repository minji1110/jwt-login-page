package minji.jwtloginpage.controller;

import lombok.RequiredArgsConstructor;
import minji.jwtloginpage.domain.User;
import minji.jwtloginpage.domain.UserDto;
import minji.jwtloginpage.domain.UserLoginDto;
import minji.jwtloginpage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebController {
    private final UserService userService;

    @GetMapping("")
    public String initialPage(Model model){

        model.addAttribute("UserLoginDto",new UserLoginDto());
        return "initial";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute @Valid UserDto userDto,BindingResult result,Model model){
        if(result.hasErrors()){
            return "initial";
        }
        else{
            User newUser=userService.signUp(userDto);
            model.addAttribute("user",newUser);
            return "home";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto, BindingResult result
            ,Model model) throws Exception
    {
        if(result.hasErrors()){
            model.addAttribute("UserLoginDto",new UserLoginDto());
            return "initial";
        }

        else {
            User user = userService.login(userLoginDto);
            model.addAttribute("user", user);
            return "home";
        }
    }
}
