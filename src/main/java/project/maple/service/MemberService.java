package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(String userEmail, String userPassword, String apiKey) {
        if (validateDuplicateMemberEmail(userEmail)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        if (validateDuplicateMemberApiKey(apiKey)) {
            throw new IllegalStateException("이미 존재하는 API입니다.");
        }

        Member member = new Member(userEmail, passwordEncoder.encode(userPassword), apiKey);
        memberRepository.save(member);
        return member.getId();
    }

    public boolean validateDuplicateMemberEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean validateDuplicateMemberApiKey(String apiKey) {
        List<Member> findMembers = memberRepository.findByApiKey(apiKey);
        if (findMembers.isEmpty()) {
            return false;
        }

        return true;
    }

}
