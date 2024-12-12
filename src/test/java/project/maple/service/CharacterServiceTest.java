package project.maple.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import project.maple.domain.Member;
import project.maple.dto.CharacterListDto;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CharacterServiceTest {

    @Autowired CharacterService characterService;
    @Autowired MemberService memberService;

    @Test
    public void get_character_list() throws Exception {
        //given
        String id = "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";


        //when
        Member member = new Member(id, password, apiKey);
        List<CharacterListDto> myCharacters = characterService.getMyCharacters(member);
        myCharacters.forEach(dto -> {
            System.out.println("ocid = " + dto.getOcid());
            System.out.println("Character_name = " + dto.getCharacter_name());
            System.out.println("Character_class = " + dto.getCharacter_class());
            System.out.println("Character_level = " + dto.getCharacter_level());
            System.out.println("World_name = " + dto.getWorld_name());
        });


        //then

    }
}