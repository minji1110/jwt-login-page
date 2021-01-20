package minji.jwtloginpage.controller;

import minji.jwtloginpage.User.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/jwt")
public class controller {
    @GetMapping("")
    public String initialPage(){
        return "initial";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto, BindingResult result){
        if(result.hasErrors()){
            return "initial";
        }
        //,,,로그인로직처리
        return "home";
    }
}
