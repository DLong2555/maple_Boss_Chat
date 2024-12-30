package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.ApprovalStatus;
import project.maple.domain.Member;
import project.maple.domain.Party;
import project.maple.domain.PartyMember;
import project.maple.dto.PartyMemberDto;
import project.maple.repository.PartyMemberRepository;
import project.maple.repository.PartyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;
    private final MemberService memberService;
    private final PartyService partyService;
    private final PartyRepository partyRepository;

    /*
    파티 가입
     */
    @Transactional
    public PartyMemberDto applyToParty(Long partyId, String userEmail, String charName, String charOcid) {
        //파티 존재 여부
        Party party = partyRepository.findById(partyId).get();

        Long findMemberId = memberService.findMemberIdByEmail(userEmail);

        //중복 신청 여부
        if (partyMemberRepository.existsByPartyIdAndMemberId(partyId, findMemberId)) {
            throw new IllegalStateException("이미 신청한 파티입니다.");
        }

        PartyMember save = partyMemberRepository.save(new PartyMember(new Member(findMemberId), charName, charOcid, party, ApprovalStatus.PENDING));

        return convertToDto(save);
    }

    /*
    가입 승인
     */
    @Transactional
    public PartyMemberDto approveMember (Long partyId, Long applicantId, String memberEmail) {

        if (!partyService.isPartyLeader(partyId, memberEmail)) {
            throw new IllegalStateException("파티 리더만 가입을 승인할 수 있습니다.");
        }

        PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, applicantId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        partyMember.approve();
        partyMemberRepository.save(partyMember);

        return convertToDto(partyMember);
    }

    /*
    가입 거절
     */
    @Transactional
    public PartyMemberDto rejectMember (Long partyId, Long applicantId, String memberEmail) {

        if (!partyService.isPartyLeader(partyId, memberEmail)) {
            throw new IllegalStateException("파티 리더만 가입을 거절할 수 있습니다.");
        }

        PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, applicantId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        partyMember.reject();
        partyMemberRepository.save(partyMember);

        return convertToDto(partyMember);
    }

    /*
    파티 탈퇴
     */
     public void leaveParty (Long partyId, String memberEmail) {

         Long findMemberId = memberService.findMemberIdByEmail(memberEmail);

         PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, findMemberId)
                .orElseThrow(() -> new IllegalArgumentException( String.format("유저(%s)가 파티(%d)에서 탈퇴하려 했으나 찾을 수 없습니다.", memberEmail, partyId)));

         partyMemberRepository.delete(partyMember);

         //파티장일 경우 파티를 삭제함.
         if (!partyService.isPartyLeader(partyId, memberEmail)) {
             partyService.handleLeaderLeaving(partyId, memberEmail);
         }
     }

     public PartyMemberDto convertToDto(PartyMember partyMember) {
         return new PartyMemberDto(
                 partyMember.getId(),
                 partyMember.getCharName(),
                 partyMember.getCharOcid(),
                 partyMember.getStatus()
         );
     }
}
