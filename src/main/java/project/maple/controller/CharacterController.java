package project.maple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.CustomUserDetails;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.character.ChooseCharInfo;
import project.maple.service.CharacterService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/characters")
    public String characterListForm(Authentication authentication, Model model) throws JsonProcessingException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (userDetails == null) {
            throw new IllegalArgumentException("저장된 정보가 없습니다.");
        }

        List<CharacterListDto> charList = characterService.getMyCharacters(userDetails.getApiKey());
        if (!charList.isEmpty()) charList.forEach(dto -> {
            log.info("dto.getCharacter_name() = {}", dto.getCharacter_name());
            log.info("dto.getCharacter_name() = {}", dto.getCharacter_level());
            log.info("dto.getCharacter_name() = {}", dto.getCharacter_class());
            log.info("dto.getCharacter_name() = {}", dto.getWorld_name());
        });
        model.addAttribute("charList", charList);
        model.addAttribute("chooseCharInfo", new ChooseCharInfo());


        return "characters";
    }


    @PostMapping("/characters")
    public String chooseCharacter(@Valid ChooseCharInfo charInfo, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails.setNowCharInfo(charInfo.getCharName(), charInfo.getOcid());

        return "/";
    }
}
