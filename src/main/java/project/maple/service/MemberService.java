package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Member;
import project.maple.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long singUp(String userEmail, String userPassword, String apiKey) {
        Member member = new Member(userEmail, passwordEncoder.encode(userPassword), passwordEncoder.encode(apiKey));
        memberRepository.save(member);
        return member.getId();
    }

}
