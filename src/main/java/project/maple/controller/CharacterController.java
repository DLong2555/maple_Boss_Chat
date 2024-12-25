package project.maple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.CustomUserDetails;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.character.ChooseCharInfo;
import project.maple.service.CharacterService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//        Set<String> worldSet = new HashSet<>();

        List<CharacterListDto> charList = characterService.getMyCharacters(userDetails.getApiKey());
        if (!charList.isEmpty()) charList.forEach(dto -> {
//            worldSet.add(dto.getWorld_name());
            log.info("dto.getCharacter_name() = {}", dto.getCharacter_name());
            log.info("dto.getCharacter_level() = {}", dto.getCharacter_level());
            log.info("dto.getCharacter_class() = {}", dto.getCharacter_class());
            log.info("dto.getWorld_name() = {}", dto.getWorld_name());
        });

//        log.info("worldSet = {}", worldSet.toString());

        model.addAttribute("charList", charList);
        model.addAttribute("chooseCharInfo", new ChooseCharInfo());
//        model.addAttribute("worldSet", worldSet);

        return "character/characters";
    }

    @PostMapping("/characters")
    public String chooseCharacter(@Valid ChooseCharInfo charInfo, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails.setNowCharInfo(charInfo.getCharName(), charInfo.getOcid());

        return "/";
    }

//    @ResponseBody
//    @GetMapping("/charactersTest")
//    public List<CharacterListDto> characterListForm_test() throws JsonProcessingException {
//
//        String api = "live_321ceb3c90be2be8a91f57a57cd693885dda2a1520e5c4a68afad772a0b0b4b6b1c45db78d00a3de8004b92575b13372";
//
//
//        Set<String> worldSet = new HashSet<>();
//
//        List<CharacterListDto> charList = characterService.getMyCharacters(api);
//        if (!charList.isEmpty()) charList.forEach(dto -> {
//            worldSet.add(dto.getWorld_name());
//            log.info("dto.getCharacter_name() = {}", dto.getCharacter_name());
//            log.info("dto.getCharacter_level() = {}", dto.getCharacter_level());
//            log.info("dto.getCharacter_class() = {}", dto.getCharacter_class());
//            log.info("dto.getWorld_name() = {}", dto.getWorld_name());
//        });
//
//        log.info("worldSet = {}", worldSet.toString());
//
//        return charList;
//    }

}
