package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.dto.LoginForm;
import project.maple.dto.LoginRequestDto;
import project.maple.dto.LoginSaveDto;
import project.maple.exception.DuplicateMemberException;
import project.maple.repository.MemberRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


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
    public LoginRequestDto login(LoginForm loginForm) {
        Member findMember = memberRepository.findByEmail(loginForm.getUserEmail()).orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), findMember.getUserPass())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return new LoginRequestDto(true, new LoginSaveDto(findMember.getUserEmail(), findMember.getApiKey()));
    }

    /*
    이메일로 멤버 id 조회
     */
    public Long findMemberIdByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 멤버를 찾을 수 없습니다."))
                .getId();
    }
}
