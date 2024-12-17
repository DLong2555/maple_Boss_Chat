package project.maple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.character.ChooseCharInfo;
import project.maple.dto.LoginSaveDto;
import project.maple.service.CharacterService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/characters")
    public String characterListForm(HttpSession session, Model model) throws JsonProcessingException {
        LoginSaveDto saveDto = (LoginSaveDto) session.getAttribute("user");
        String email = "maxol2558@gmail.com";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

//        List<CharacterListDto> charList = characterService.getMyCharacters(saveDto);
        List<CharacterListDto> charList = characterService.getMyCharacters(new LoginSaveDto(email, apiKey));
        if(!charList.isEmpty()) charList.forEach(dto -> {
            System.out.println("dto.getCharacter_name() = " + dto.getCharacter_name());
            System.out.println("dto.getCharacter_name() = " + dto.getCharacter_level());
            System.out.println("dto.getCharacter_name() = " + dto.getCharacter_class());
            System.out.println("dto.getCharacter_name() = " + dto.getWorld_name());
            System.out.println("=================================================");
        });
        model.addAttribute("charList", charList);
        model.addAttribute("chooseCharInfo", new ChooseCharInfo());

        return "characters";
    }

    @PostMapping("/characters")
    public String chooseCharacter(@Valid ChooseCharInfo charInfo, HttpSession session){
        LoginSaveDto saveDto = (LoginSaveDto) session.getAttribute("user");
        saveDto.saveChooseCharInfo(charInfo);

        session.setAttribute("user", saveDto);

        return "/";
    }
}
