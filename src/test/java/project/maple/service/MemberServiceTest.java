package project.maple.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.dto.member.JoinForm;
import project.maple.dto.member.LoginForm;
import project.maple.dto.member.LoginRequestDto;
import project.maple.exception.DuplicateMemberException;
import project.maple.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void email_and_apiKey_duplicate_test() throws Exception {
        //given
        String email1 = "test@test.com";
        String password = "qwerty1234";
        String api1 = "live_321ceb3c90be2be8a91f57a57cd693885dda2a1520e5c4a68afad772a0b0b4b6b1c45db78d00a3de8004b92575b13372";

        String email2 = "maxol2558@gmail.com";
        String api2 = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        JoinForm joinForm1 = new JoinForm(email1, password, api1);

        //when
        memberService.join(joinForm1);

        //then
        // 이메일 중복 시 에러 발생 테스트
        assertThrows(DuplicateMemberException.class, () -> memberService.join(new JoinForm(email1, password, api2)));

        // api 중복 시 에러 발생 테스트
        assertThrows(DuplicateMemberException.class, () -> memberService.join(new JoinForm(email2, password, api1)));
    }

    @Test
    public void join_and_duplicate_test() throws Exception {
        //given
        String email = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        Member member = new Member(email, password, apiKey);

        //when
        Long memberId = memberService.join(new JoinForm(email,password,apiKey));

        //then
        Optional<Member> findEntity = memberRepository.findById(memberId);
        Member findMember = findEntity.orElseThrow(EntityNotFoundException::new);

        // 회원 가입 잘 되었는지 테스트
        assertThat(findMember.getId()).isEqualTo(memberId);

        // 아이디 확인
        assertThat(findMember.getUsername()).isEqualTo(email);

        // 비밀번호 확인
        assertTrue(passwordEncoder.matches(member.getUserPass(), findMember.getUserPass()));

        // api key 확인
        assertThat(member.getApiKey()).isEqualTo(findMember.getApiKey());
    }

    @Test
    public void login_test() throws Exception {
        //given
        String email = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        //when
        memberService.join(new JoinForm(email,password,apiKey));

        em.flush();
        em.clear();

        //then
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail(email);
        loginForm.setPassword(password);

        //로그인 성공시
        LoginRequestDto result_success = memberService.login(loginForm);
        assertTrue(result_success.isSuccess());
        assertThat(result_success.getSaveDto().getEmail()).isEqualTo(email);
        assertThat(result_success.getSaveDto().getApiKey()).isEqualTo(apiKey);

        //없는 이메일일 시
        loginForm.setEmail("");
        assertThrows(IllegalStateException.class, () -> memberService.login(loginForm));

        //비밀번호가 틀릴 시
        loginForm.setEmail(email);
        loginForm.setPassword("!234");
        assertThrows(IllegalStateException.class, () -> memberService.login(loginForm));
    }
}