package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Party;
import project.maple.repository.PartyMemberRepository;
import project.maple.repository.PartyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMemberRepository partyMemberRepository;

    /*
    파티 생성
     */
    @Transactional
    public Party createParty(Party party) {
        return partyRepository.save(party);
    }

    /*
    특정 파티 조회
     */
    public Party findById(Long partyId) {
        return partyRepository.findById(partyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
    }

    /*
    모든 파티 조회
     */
    public List<Party> findParties() {
        return partyRepository.findAll();
    }

    /*
    파티 삭제
     */
    @Transactional
    public void deleteParty(Long partyId, String userEmail) {
        if (isPartyLeader(partyId, userEmail)) {
            throw new IllegalArgumentException("파티장만 파티를 삭제할 수 있습니다.");
        }
        partyRepository.deleteById(partyId);
    }

    /*
    파티장 확인 메서드
     */
    public boolean isPartyLeader(Long partyId, String userEmail) {
        Party findParty = partyRepository.findById(partyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
        return findParty.getMember().getUserEmail().equals(userEmail);
    }

    public void handleLeaderLeaving(Long partyId, String userEmail) {
        // 1. 파티의 다른 멤버가 있는지 확인
        boolean hasOtherMembers = partyMemberRepository.existsById(partyId);
        if (hasOtherMembers) {
            throw new IllegalStateException("파티장이 탈퇴할 수 없습니다. 다른 멤버가 존재합니다.");
        }

        // 2. 파티 삭제
        deleteParty(partyId, userEmail);
    }
}