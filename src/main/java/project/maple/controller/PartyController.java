package project.maple.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.maple.domain.Party;
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
    public String getParty(Model model, Long partyId) {
        Party party = partyService.findById(partyId);
        model.addAttribute("party", party);
        return "parties";
    }

    /*
    파티 생성
     */
    
    /*
    파티 삭제
     */
}
