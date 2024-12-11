package project.maple.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Difficulty;
import project.maple.domain.Member;
import project.maple.domain.Party;
import project.maple.domain.PartyState;
import project.maple.repository.MemberRepository;
import project.maple.repository.PartyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PartyServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PartyService partyService;

    @Test
    void 파티_생성_테스트() {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

        Long memberId = memberService.signUp(id, password, apiKey);

        Optional<Member> member = memberRepository.findById(memberId);

        //when
        Party party = new Party(member.get(), "흰여우", "파티생성테스트", "자쿰", Difficulty.NORMAL, 4);
        partyService.createParty(party);

        //then
        // - 기본 정보 확인
        assertThat(party.getPartyName()).isEqualTo("파티생성테스트");
        assertThat(party.getBoss()).isEqualTo("자쿰");
        assertThat(party.getDifficulty()).isEqualTo(Difficulty.NORMAL);
        assertThat(party.getMaxMemberCount()).isEqualTo(4);
        assertThat(party.getPartyState()).isEqualTo(PartyState.OPEN);
        assertThat(party.getMember()).isEqualTo(member.get());

        // - 파티를 생성한 멤버와의 연관관계 확인
        assertThat(party.getMember().getId()).isEqualTo(memberId);
    }
}