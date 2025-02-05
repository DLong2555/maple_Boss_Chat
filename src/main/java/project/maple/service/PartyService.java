package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.ApprovalStatus;
import project.maple.domain.Member;
import project.maple.domain.Party;
import project.maple.domain.PartyMember;
import project.maple.dto.CustomUserDetails;
import project.maple.dto.PartyForm;
import project.maple.dto.PartyInfoDto;
import project.maple.dto.PartyMemberDto;
import project.maple.repository.PartyMemberRepository;
import project.maple.repository.PartyRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public Long createParty(PartyForm partyForm, CustomUserDetails userDetails) {
        //Member member, String charName, String charOcid, String partyName, String boss, Difficulty difficulty, int maxMemberCount

        Party party = new Party(userDetails.getMember(), userDetails.getCharName(), userDetails.getOcid(), partyForm.getPartyName(), partyForm.getBoss(), partyForm.getDifficulty(), partyForm.getMaxMemberCount());

        partyRepository.save(party);

        return party.getId();
    }

    /*
    특정 파티 조회
     */
    public PartyInfoDto findById(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
        return convertToDto(party);
        //return partyRepository.findById(partyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
    }

    /*
    모든 파티 조회
     */
    public List<PartyInfoDto> findParties() {
        List<Party> parties = partyRepository.findAll();
        return parties.stream()
                .map(this :: convertToDto)
                .collect(Collectors.toList());
    }

    /*
    파티 삭제
     */
    @Transactional
    public void deleteParty(Long partyId, String userEmail) {
        if (!isPartyLeader(partyId, userEmail)) {
            throw new IllegalArgumentException("파티장만 파티를 삭제할 수 있습니다.");
        }
        partyRepository.deleteById(partyId);
    }

    /*
    파티장 확인 메서드
     */
    public boolean isPartyLeader(Long partyId, String userEmail) {
        Party findParty = partyRepository.findById(partyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
        return findParty.getMember().getUsername().equals(userEmail);
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

    public PartyInfoDto convertToDto(Party party) {
        List<PartyMemberDto> members = partyMemberRepository.findByPartyAndStatus(party, ApprovalStatus.APPROVED)
                .stream()
                .map(partyMember -> new PartyMemberDto(
                partyMember.getId(),
                partyMember.getCharName(),
                partyMember.getCharOcid(),
                partyMember.getStatus()
        ))
                .collect(Collectors.toList());

        List<PartyMemberDto> applicants = partyMemberRepository.findByPartyAndStatus(party, ApprovalStatus.PENDING)
                .stream()
                .map(partyMember -> new PartyMemberDto(
                        partyMember.getId(),
                        partyMember.getCharName(),
                        partyMember.getCharOcid(),
                        partyMember.getStatus()
                ))
                .collect(Collectors.toList());

        return new PartyInfoDto(
                party.getId(),
                party.getPartyName(),
                party.getBoss(),
                party.getDifficulty(),
                applicants,
                party.getMaxMemberCount(),
                members,
                party.getPartyState(),
                party.getCharName()
        );
    }
}