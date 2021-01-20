package minji.jwtloginpage.controller;

import minji.jwtloginpage.User.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/jwt")
public class WebController {
    @GetMapping("")
    public String initialPage(Model model){

        model.addAttribute("UserLoginDto",new UserLoginDto());
        return "initial";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto, BindingResult result
            ,Model model)
    {
        if(result.hasErrors()){
            model.addAttribute("UserLoginDto",new UserLoginDto());
            return "initial";
        }
        //,,,로그인로직처리
        return "home";
    }
}
