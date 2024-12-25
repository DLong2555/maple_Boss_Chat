package project.maple;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.maple.domain.MemberRole;
import project.maple.dto.member.JoinForm;
import project.maple.service.MemberService;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.join(new JoinForm("maxol2558@gmail.com", "1234", "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d"));
    }
}
