package project.maple;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.maple.domain.Party;
import project.maple.dto.member.JoinForm;
import project.maple.repository.MemberRepository;
import project.maple.service.MemberService;
import project.maple.service.PartyService;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final PartyService partyService;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        memberService.join(new JoinForm("maxol2558@gmail.com", "1234", "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d"));
        memberService.join(new JoinForm("wogks119@gmail.com", "1234", "live_321ceb3c90be2be8a91f57a57cd693885dda2a1520e5c4a68afad772a0b0b4b6b1c45db78d00a3de8004b92575b13372"));
    }
}
