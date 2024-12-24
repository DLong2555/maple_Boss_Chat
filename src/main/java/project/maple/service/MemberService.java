package project.maple.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.dto.member.JoinForm;
import project.maple.exception.DuplicateMemberException;
import project.maple.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(JoinForm joinForm) {
        if (validateDuplicateMember("email", joinForm.getEmail())) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        if (validateDuplicateMember("apiKey", joinForm.getApiKey())) {
            throw new DuplicateMemberException("이미 존재하는 API입니다.");
        }

        Member member = new Member(joinForm.getEmail(), passwordEncoder.encode(joinForm.getPassword()), joinForm.getApiKey());
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복체크
     */
    public boolean validateDuplicateMember(String field, String value) {
        if (field.equals("email")) {
            return memberRepository.findByEmail(value).isPresent();
        } else if (field.equals("apiKey")) {
            return memberRepository.findByApiKey(value).isPresent();
        }

        throw new IllegalArgumentException("Invalid field type");
    }

    /**
     * 로그인
     */
//    public LoginRequestDto login(LoginForm loginForm) {
//        Member findMember = memberRepository.findByEmail(loginForm.getUsername()).orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다."));
//
//        if (!passwordEncoder.matches(loginForm.getPassword(), findMember.getUserPass())) {
//            throw new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다.");
//        }
//
//        return new LoginRequestDto(true, new LoginSaveDto(findMember.getUsername(), findMember.getApiKey()));
//    }


    /*
    이메일로 멤버 id 조회
     */

    public Long findMemberIdByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 멤버를 찾을 수 없습니다."))
                .getId();
    }
}
