package project.maple.domain;

import jakarta.persistence.Column;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import project.maple.repository.PartyRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PartyTest {
    @Autowired
    private PartyRepository partyRepository;

    @Test
    void 파티_엔티티_테스트() {
        //given
        Member member = new Member("testId@test.test", "testPass", "test_api_key");

        String charName = "test";
        String charOcid = "test";
        String partyName = "test"; //파티명 또는 파티 설명
        String boss = "test";
        Difficulty difficulty = Difficulty.HARD; // 난이도
        int maxMemberCount = 4; // 최대 모집 인원수

        Party party_null_member = new Party(null, charName, charOcid, partyName, boss, difficulty, maxMemberCount);
        Party party_null_charName = new Party(member, null, charOcid, partyName, boss, difficulty, maxMemberCount);
        Party party_null_partyName = new Party(member, charName, charOcid, null, boss, difficulty, maxMemberCount);
        Party party_null_boss = new Party(member, charName, charOcid, partyName, null, difficulty, maxMemberCount);
        Party party_null_difficulty = new Party(member, charName, charOcid, partyName, boss, null, maxMemberCount);

        //when

        //then
        // member가 null인 경우
        assertThrows(ConstraintViolationException.class, () -> partyRepository.saveAndFlush(party_null_member), "파티장 : 필수 정보입니다.");

        // charName이 null인 경우
        assertThrows(InvalidDataAccessApiUsageException.class, () -> partyRepository.saveAndFlush(party_null_charName), "파티명 : 필수 정보입니다.");

        // partyName이 null인 경우
        assertThrows(InvalidDataAccessApiUsageException.class, () -> partyRepository.saveAndFlush(party_null_partyName), "파티명 : 필수 정보입니다.");

        // boss가 null인 경우
        assertThrows(InvalidDataAccessApiUsageException.class, () -> partyRepository.saveAndFlush(party_null_boss), "보스 : 필수 정보입니다.");

        // difficulty가 null인 경우
        assertThrows(InvalidDataAccessApiUsageException.class, () -> partyRepository.saveAndFlush(party_null_difficulty), "난이도 : 필수 정보입니다.");
    }
}