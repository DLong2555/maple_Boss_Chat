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
import org.springframework.web.bind.annotation.ResponseBody;
import project.maple.dto.CustomUserDetails;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.character.ChooseCharInfo;
import project.maple.service.CharacterService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

        Map<String, List<CharacterListDto>> myCharacters = characterService.getMyCharacters(userDetails.getUsername(), userDetails.getApiKey());
//        for (String s : myCharacters.keySet()) {
//            myCharacters.get(s).forEach(characterListDto -> {
//                log.info("name = {}", characterListDto.getCharacter_name());
//                log.info("world = {}", characterListDto.getWorld_name());
//            });
//        }

        // 레벨 순 같으면 이름순으로 정렬
        for (String world : myCharacters.keySet()) {
            myCharacters.get(world).sort(((o1, o2) -> {
                if (o2.getCharacter_level() == o1.getCharacter_level())
                    return o1.getCharacter_name().compareTo(o2.getCharacter_name());
                else return o2.getCharacter_level() - o1.getCharacter_level();
            }));
        }

        model.addAttribute("chooseCharInfo", new ChooseCharInfo());
        model.addAttribute("characterMap", myCharacters);
        model.addAttribute("worldKeys", myCharacters.keySet());

        return "character/characters";
    }

    @PostMapping("/select")
    public String chooseCharacter(@Valid ChooseCharInfo charInfo, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails.setNowCharInfo(charInfo.getCharName(), charInfo.getOcid());

        log.info("charName = {}", userDetails.getCharName());
        log.info("charOcid = {}", userDetails.getOcid());

        return "redirect:/";
    }

//    @ResponseBody
//    @GetMapping("/charactersTest")
//    public Map<String, List<CharacterListDto>> characterListForm_test() throws JsonProcessingException {
//
//        String api1 = "live_321ceb3c90be2be8a91f57a57cd693885dda2a1520e5c4a68afad772a0b0b4b6b1c45db78d00a3de8004b92575b13372";
//        String api2 = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
//
//        Map<String, List<CharacterListDto>> myCharacters = characterService.getMyCharacters("testEmail", api1);
//
//        return myCharacters;
//    }

}
