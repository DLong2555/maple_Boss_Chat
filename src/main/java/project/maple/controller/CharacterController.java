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

    @GetMapping("/charaters")
    public String characterListForm(HttpSession session, Model model) throws JsonProcessingException {
        Member user = (Member) session.getAttribute("user");

        characterService.getMyCharacters(user);

        List<CharacterListDto> charList = List.of();
        model.addAttribute("charList", charList);

        return "character/charaters";
    }
}
