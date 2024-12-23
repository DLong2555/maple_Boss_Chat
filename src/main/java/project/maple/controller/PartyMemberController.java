package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.member.LoginSaveDto;
import project.maple.service.PartyMemberService;

@Controller
@RequiredArgsConstructor
public class PartyMemberController {
    private final PartyMemberService partyMemberService;

    /*
    파티 가입
     */
    @PostMapping(value = "/party/{partyId}/join")
    public String applyToParty(@PathVariable Long partyId, HttpSession session) {
        LoginSaveDto user = (LoginSaveDto) session.getAttribute("user");
        String userEmail = user.getEmail();
        String nowCharOcid = user.getNowCharOcid();
        String nowCharName = user.getNowCharName();

        partyMemberService.applyToParty(partyId, userEmail, nowCharName, nowCharOcid);
        return "redirect:/parties";
    }
    /*
    파티 승인
     */
    @PostMapping(value = "/party/{partyId}/approve")
    public String approveToParty(@PathVariable Long partyId, HttpSession session) {
        LoginSaveDto user = (LoginSaveDto) session.getAttribute("user");
        partyMemberService.approveMember(partyId, user.getEmail());
        return "redirect:/parties";
    }

    /*
    파티 거절
     */
    @PostMapping(value = "/party/{partyId}/reject")
    public String rejectToParty(@PathVariable Long partyId, HttpSession session) {
        LoginSaveDto user = (LoginSaveDto) session.getAttribute("user");
        partyMemberService.rejectMember(partyId, user.getEmail());
        return "redirect:/parties";
    }

    /*
    파티 탈퇴
     */
    @PostMapping(value = "/party/{partyId}/leave")
    public String leaveParty(@PathVariable Long partyId, HttpSession session) {
        LoginSaveDto user = (LoginSaveDto) session.getAttribute("user");
        partyMemberService.leaveParty(partyId, user.getEmail());
        return "redirect:/parties";
    }
}