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

    @Autowired
    private PartyRepository partyRepository;

    @Test
    @Rollback(value = false)
    void 파티_생성_테스트() {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        String ocid = "test";

        Long memberId = memberService.signUp(id, password, apiKey);

        Optional<Member> member = memberRepository.findById(memberId);

        //when
        for (int i = 0; i < 5; i++) {
            Party party = new Party(member.get(), "흰여우", ocid, "파티생성테스트" + i, "자쿰", Difficulty.NORMAL, 4);
            partyService.createParty(party);
        }

//        Party party = new Party(member.get(), "흰여우", "파티생성테스트", "자쿰", Difficulty.NORMAL, 4);
//        partyService.createParty(party);
//
//        //then
//        // - 기본 정보 확인
//        assertThat(party.getPartyName()).isEqualTo("파티생성테스트");
//        assertThat(party.getBoss()).isEqualTo("자쿰");
//        assertThat(party.getDifficulty()).isEqualTo(Difficulty.NORMAL);
//        assertThat(party.getMaxMemberCount()).isEqualTo(4);
//        assertThat(party.getPartyState()).isEqualTo(PartyState.OPEN);
//        assertThat(party.getMember()).isEqualTo(member.get());
//
//        // - 파티를 생성한 멤버와의 연관관계 확인
//        assertThat(party.getMember().getId()).isEqualTo(memberId);
    }

    @Test
    void 파티_조회_테스트() {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        String ocid = "test";

        Long memberId = memberService.signUp(id, password, apiKey);

        Optional<Member> member = memberRepository.findById(memberId);

        Party party = partyService.createParty(new Party(member.get(), "흰여우", ocid, "파티생성테스트", "자쿰", Difficulty.NORMAL, 4));

        //when
        Party findParty = partyRepository.findById(party.getId()).get();

        //then
        assertThat(findParty).isEqualTo(party);
        assertThat(findParty.getId()).isEqualTo(party.getId());
        assertThat(findParty.getPartyName()).isEqualTo("파티생성테스트");
        assertThat(findParty.getBoss()).isEqualTo("자쿰");
        assertThat(findParty.getDifficulty()).isEqualTo(Difficulty.NORMAL);
        assertThat(findParty.getMaxMemberCount()).isEqualTo(4);
        assertThat(findParty.getPartyState()).isEqualTo(PartyState.OPEN);
        assertThat(findParty.getMember()).isEqualTo(member.get());
        assertThat(findParty.getMember().getId()).isEqualTo(memberId);
    }

    @Test
    void 파티_삭제_테스트() {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        String ocid = "test";

        Long memberId = memberService.signUp(id, password, apiKey);

        Optional<Member> member = memberRepository.findById(memberId);

        Party party = partyService.createParty(new Party(member.get(), "흰여우", ocid, "파티생성테스트", "자쿰", Difficulty.NORMAL, 4));

        //when
        partyService.deleteParty(party.getId(), "maxol2558@gmail.com");

        //then
        assertThat(partyRepository.findById(party.getId())).isEmpty(); // 파티가 삭제되었는지 확인

    }
}