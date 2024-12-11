package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.LoginForm;
import project.maple.dto.LoginRequestDto;
import project.maple.repository.MemberRepository;
import project.maple.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(HttpSession session, LoginForm loginForm, BindingResult result) {

        if(result.hasErrors()) {
            return "members/loginForm";
        }

        LoginRequestDto loginRequest = memberService.login(loginForm);
        if (loginRequest.isSuccess()){
            session.setAttribute("loginUser", loginRequest.getMember());

            return "redirect:/";
        }

        return "members/loginForm";
    }


}
