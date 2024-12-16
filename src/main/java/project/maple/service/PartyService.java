package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Party;
import project.maple.repository.PartyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

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
    public void deleteParty(Long partyId) {
        partyRepository.deleteById(partyId);
    }
}