package project.maple.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void email_and_apiKey_duplicate_test() throws Exception {
        //given
        String email1 = "test@test.com";
        String password = "qwerty1234";
        String api1 = "live_321ceb3c90be2be8a91f57a57cd693885dda2a1520e5c4a68afad772a0b0b4b6b1c45db78d00a3de8004b92575b13372";

        String email2 = "maxol2558@gmail.com";
        String api2 = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        //when
        memberService.signUp(email1, password, api1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.signUp(email1, password, api2));
        assertThrows(IllegalStateException.class, () -> memberService.signUp(email2, password, api1));
    }

    @Test
    public void signUp_and_duplicate_test() throws Exception {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        Member member = new Member(id, password, apiKey);

        //when
        Long memberId = memberService.signUp(id,password,apiKey);

        //then
        Optional<Member> findEntity = memberRepository.findById(memberId);
        Member findMember = findEntity.orElseThrow(EntityNotFoundException::new);

        // 회원 가입 잘 되었는지 테스트
        assertThat(findMember.getId()).isEqualTo(memberId);

        // 아이디 확인
        assertThat(findMember.getUserEmail()).isEqualTo(id);

        // 비밀번호 확인
        assertTrue(passwordEncoder.matches(member.getUserPass(), findMember.getUserPass()));

        // api key 확인
        assertThat(member.getApiKey()).isEqualTo(findMember.getApiKey());
    }
}