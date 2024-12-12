package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.ApprovalStatus;
import project.maple.domain.Member;
import project.maple.domain.Party;
import project.maple.domain.PartyMember;
import project.maple.repository.MemberRepository;
import project.maple.repository.PartyMemberRepository;
import project.maple.repository.PartyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMemberRepository partyMemberRepository;
    private final MemberService memberService;
    private final PartyService partyService;

    /*
    파티 가입
     */
    @Transactional
    public PartyMember applyToParty(Long partyId, String memberId, String charName) {
        //파티 존재 여부
        Party party = partyService.findById(partyId);

        Long findMemberId = memberService.findMemberIdByEmail(memberId);

        //중복 신청 여부
        if (partyMemberRepository.existsByPartyIdAndMemberId(partyId, findMemberId)) {
            throw new IllegalStateException("이미 신청한 파티입니다.");
        }

        PartyMember partyMember = new PartyMember(new Member(findMemberId), charName, party, ApprovalStatus.PENDING);

        return partyMemberRepository.save(partyMember);
    }

    /*
    가입 승인
     */
    @Transactional
    public PartyMember approveMember (Long partyId, String memberId) {

        Long findMemberId = memberService.findMemberIdByEmail(memberId);

        PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, findMemberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        partyMember.approve();

        return partyMemberRepository.save(partyMember);
    }

    /*
    가입 거절
     */
    @Transactional
    public PartyMember rejectMember (Long partyId, String memberId) {

        Long findMemberId = memberService.findMemberIdByEmail(memberId);

        PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, findMemberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        partyMember.reject();

        return partyMemberRepository.save(partyMember);
    }

    /*
    파티 탈퇴
     */
     public void leaveParty (Long partyId, String memberId) {

         Long findMemberId = memberService.findMemberIdByEmail(memberId);

         PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, findMemberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

         partyMemberRepository.delete(partyMember);
     }
}
