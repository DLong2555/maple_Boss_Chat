package project.maple.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.LoginSaveDto;
import project.maple.dto.character.CharacterStatDto;
import project.maple.dto.character.item.ItemBaseOption;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CharacterServiceTest {

    @Autowired CharacterService characterService;
    @Autowired MemberService memberService;

    static String email = "maxol2558@gmail.com";
    static String password = "zkzktl25@#";
    static String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";

    @Test
    public void get_character_list() throws Exception {
        //given

        //when
        List<CharacterListDto> myCharacters = characterService.getMyCharacters(new LoginSaveDto(email, apiKey));
//        myCharacters.forEach(dto -> {
//            System.out.println("ocid = " + dto.getOcid());
//            System.out.println("Character_name = " + dto.getCharacter_name());
//            System.out.println("Character_class = " + dto.getCharacter_class());
//            System.out.println("Character_level = " + dto.getCharacter_level());
//            System.out.println("World_name = " + dto.getWorld_name());
//        });

        //then
        //첫번째 테스트
        assertThat(myCharacters.get(0).getCharacter_name()).isEqualTo("프테뇽");
        assertThat(myCharacters.get(0).getCharacter_class()).isEqualTo("다크나이트");
        assertThat(myCharacters.get(0).getCharacter_level()).isEqualTo(285);
        assertThat(myCharacters.get(0).getWorld_name()).isEqualTo("크로아");
        assertThat(myCharacters.get(0).getOcid()).isEqualTo("afe73d5cae7a7195657deca902b2f965");

        //마지막 테스트
        assertThat(myCharacters.get(myCharacters.size() - 1).getCharacter_name()).isEqualTo("양갈래몰랑");
        assertThat(myCharacters.get(myCharacters.size() - 1).getCharacter_class()).isEqualTo("제논");
        assertThat(myCharacters.get(myCharacters.size() - 1).getCharacter_level()).isEqualTo(1);
        assertThat(myCharacters.get(myCharacters.size() - 1).getWorld_name()).isEqualTo("제니스");
        assertThat(myCharacters.get(myCharacters.size() - 1).getOcid()).isEqualTo("b8676d0124d69aaa46bb291951d34245");

    }

    @Test
    public void get_character_stat_test() throws Exception {
        //given
        String ocid = "afe73d5cae7a7195657deca902b2f965";

        //when
        List<CharacterStatDto> characterStat = characterService.getCharacterStat(ocid);

        for (CharacterStatDto stat : characterStat) {
            System.out.println(stat.getStat_name() + " : " + stat.getStat_value());
        }
        //then

    }
}