package project.maple.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.domain.Party;
import project.maple.dto.LoginSaveDto;
import project.maple.service.PartyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    /*
    파티 조회
     */
    @GetMapping(value = "/parties")
    public String getParties(Model model) {
        List<Party> parties = partyService.findParties();
        model.addAttribute("parties", parties);
        return "parties";
    }

    @GetMapping(value = "/party/{partyId}")
    public String getParty(Model model, @PathVariable Long partyId) {
        Party party = partyService.findById(partyId);
        model.addAttribute("party", party);
        return "parties";
    }

    /*
    파티 생성
     */
    @PostMapping(value = "/party/create")
    public String createParty(Party party) {
        partyService.createParty(party);
        return "redirect:/parties";
    }
    
    /*
    파티 삭제
     */
    @PostMapping(value = "/party/{partyId}/delete")
    public String deleteParty(@PathVariable Long partyId, HttpSession session) {
        LoginSaveDto user = (LoginSaveDto) session.getAttribute("user");
        String userEmail = user.getUserEmail();
        partyService.deleteParty(partyId, userEmail);
        return "redirect:/parties";
    }
}
