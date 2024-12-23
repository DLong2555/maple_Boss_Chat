package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.member.JoinForm;
import project.maple.dto.member.LoginForm;
import project.maple.dto.member.LoginRequestDto;
import project.maple.service.LoginService;
import project.maple.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new JoinForm());

        return "member/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm, BindingResult result) {

        if (result.hasErrors()) {
            return "member/joinForm";
        }

        log.info("joinForm email: {}", joinForm.getEmail());
        log.info("joinForm pass: {}", joinForm.getPassword());
        log.info("joinForm api: {}", joinForm.getApiKey());
        memberService.join(joinForm);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "member/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@Valid LoginForm loginForm, BindingResult result, HttpSession session) {
//
//        log.info("loginForm email: {}", loginForm.getEmail());
//
//        if(result.hasErrors()) {
//            return "member/loginForm";
//        }
//
//        LoginRequestDto loginRequest = memberService.login(loginForm);
//        log.info("login request: {}", loginRequest);
//
//        if (loginRequest.isSuccess()){
//
//            UsernamePasswordAuthenticationToken authenticationToken
//                    = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
//            authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//            session.setAttribute("loginUser", loginRequest.getSaveDto());
//
//            return "redirect:/characters";
//        }
//
//        return "/";
//    }

}
