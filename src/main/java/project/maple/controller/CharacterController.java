package project.maple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.maple.domain.Member;
import project.maple.dto.CharacterListDto;
import project.maple.service.CharacterService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/characters")
    public String characterListForm(HttpSession session, Model model) throws JsonProcessingException {
//        Member member = (Member) session.getAttribute("user");
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        Member member = new Member(id, password, apiKey);

        List<CharacterListDto> charList = characterService.getMyCharacters(member);
        if(!charList.isEmpty()) charList.forEach(dto -> {
            System.out.println("dto.getCharacter_name() = " + dto.getCharacter_name());
        });
        model.addAttribute("charList", charList);

        return "characters";
    }
}
