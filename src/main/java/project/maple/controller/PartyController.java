package project.maple.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.CustomUserDetails;
import project.maple.dto.PartyForm;
import project.maple.dto.PartyInfoDto;
import project.maple.service.PartyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartyController {
    private final PartyService partyService;

    /*
    파티 조회
     */
    @GetMapping(value = "/party/parties")
    public String getParties(Model model) {
        List<PartyInfoDto> parties = partyService.findParties();
        model.addAttribute("parties", parties);
        return "party/parties";
    }

    @GetMapping(value = "/party/{partyId}")
    public String getParty(@PathVariable Long partyId, Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        PartyInfoDto party = partyService.findById(partyId);

        boolean isPartyLeader = partyService.isPartyLeader(partyId, userDetails.getUsername());

        model.addAttribute("party", party);
        model.addAttribute("isPartyLeader", isPartyLeader);

        return "/party/partyDetail";
    }

    /*
    파티 생성
     */
    @GetMapping(value = "/party/create")
    public String createPartyForm(Model model) {
        model.addAttribute("partyForm", new PartyForm());
        return "party/createParty";
    }

    @PostMapping(value = "/party/create")
    public String createParty(@Valid PartyForm partyForm, BindingResult result, Authentication authentication, Model model) {
        log.info("파티명: {}", partyForm.getPartyName());
        log.info("난이도: {}", partyForm.getDifficulty());
        log.info("보스", partyForm.getBoss());

        if (result.hasErrors()) {
            // 폼에 오류가 있는 경우, 다시 폼 페이지로 이동
            model.addAttribute("partyForm", partyForm);
            return "party/createParty";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        partyService.createParty(partyForm, userDetails);
        return "redirect:/";
    }


    /*
    파티 삭제
     */
    @PostMapping(value = "/party/{partyId}/delete")
    public String deleteParty(@PathVariable Long partyId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        String userEmail = userDetails.getUsername();
        partyService.deleteParty(partyId, userEmail);
        return "redirect:/parties";
    }
}
