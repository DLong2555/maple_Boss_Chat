package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.LoginForm;
import project.maple.dto.LoginRequestDto;
import project.maple.dto.LoginSaveDto;
import project.maple.repository.MemberRepository;
import project.maple.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, HttpSession session, BindingResult result) {

        if(result.hasErrors()) {
            return "login";
        }

        LoginRequestDto loginRequest = memberService.login(loginForm);
        if (loginRequest.isSuccess()){
            session.setAttribute("loginUser", loginRequest.getSaveDto());

            return "redirect:/characters";
        }

        return "login";
    }


}
