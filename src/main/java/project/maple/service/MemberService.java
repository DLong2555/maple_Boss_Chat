package project.maple.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.dto.LoginForm;
import project.maple.dto.LoginRequestDto;
import project.maple.exception.DuplicateMemberException;
import project.maple.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

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
    public Long signUp(String userEmail, String userPassword, String apiKey) {
        if (validateDuplicateMember("email", userEmail)) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        if (validateDuplicateMember("apiKey", apiKey)) {
            throw new DuplicateMemberException("이미 존재하는 API입니다.");
        }

        Member member = new Member(userEmail, passwordEncoder.encode(userPassword), apiKey);
        memberRepository.save(member);
        return member.getId();
    }

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
    public LoginRequestDto login(LoginForm loginForm) {
        Member findMember = memberRepository.findByEmail(loginForm.getUserEmail()).orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), findMember.getUserPass())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return new LoginRequestDto(true, "success", findMember);
    }
}
