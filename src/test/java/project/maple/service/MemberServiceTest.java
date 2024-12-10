package project.maple.service;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void 회원가입() throws Exception {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        Member member = new Member(id, password, apiKey);

        //when
        Long memberId = memberService.singUp(id,password,apiKey);

        //then
        List<Member> findMember = memberRepository.findAll();
        assertThat(findMember.get(0).getId()).isEqualTo(memberId);
        assertThat(passwordEncoder.matches(member.getUserPass(), findMember.get(0).getUserPass())).isTrue();
        assertThat(passwordEncoder.matches(member.getApiKey(), findMember.get(0).getApiKey())).isTrue();

    }
}