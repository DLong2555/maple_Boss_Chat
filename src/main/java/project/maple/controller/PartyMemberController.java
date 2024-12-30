package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.CustomUserDetails;
import project.maple.service.PartyMemberService;

@Controller
@RequiredArgsConstructor
public class PartyMemberController {
    private final PartyMemberService partyMemberService;

    /*
    파티 가입
     */
    @PostMapping(value = "/party/{partyId}/join")
    public String applyToParty(@PathVariable Long partyId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        String userEmail = userDetails.getUsername();
        String nowCharOcid = userDetails.getOcid();
        String nowCharName = userDetails.getCharName();

        partyMemberService.applyToParty(partyId, userEmail, nowCharName, nowCharOcid);
        return "redirect:/party/parties";
    }
    /*
    파티 승인
     */
    @PostMapping(value = "/party/{partyId}/{applicantId}/approve")
    public String approveToParty(@PathVariable Long partyId, Authentication authentication, @PathVariable Long applicantId) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        partyMemberService.approveMember(partyId, applicantId, userDetails.getUsername());
        return "redirect:/party/parties";
    }

    /*
    파티 거절
     */
    @PostMapping(value = "/party/{partyId}/{applicantId}/reject")
    public String rejectToParty(@PathVariable Long partyId, Authentication authentication, @PathVariable Long applicantId) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        partyMemberService.rejectMember(partyId, applicantId, userDetails.getUsername());
        return "redirect:/party/parties";
    }

    /*
    파티 탈퇴
     */
    @PostMapping(value = "/party/{partyId}/leave")
    public String leaveParty(@PathVariable Long partyId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        partyMemberService.leaveParty(partyId, userDetails.getUsername());
        return "redirect:/party/parties";
    }
}