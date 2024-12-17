package project.maple.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.LoginSaveDto;
import project.maple.dto.character.CharacterStatDto;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CharacterService {

    static String baseUrl = "https://open.api.nexon.com";
    static WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
    static String admin_api_key = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
    static ObjectMapper mapper;

    /**
     * 캐릭터 리스트 조회
     */
    @Cacheable(cacheNames = "getCharacters", key = "'character_list'", cacheManager = "charactersCacheManager")
    public List<CharacterListDto> getMyCharacters(LoginSaveDto saveDto) throws JsonProcessingException {

        // Json으로 내 캐릭터 리스트 가져오기
        JsonNode myCharacters = getMyCharactersJSON(saveDto.getApiKey(), "/maplestory/v1/character/list", null);

        // 내 캐릭터 리스트 json -> CharacterListDto
        return getCharacterListDtos(myCharacters);
    }

    public List<CharacterListDto> getCharacterListDtos(JsonNode myCharacters) throws JsonProcessingException {
        mapper = new ObjectMapper();

        // dto로 파싱
        List<CharacterListDto> characterList = mapper.convertValue(
                myCharacters.findValue("character_list"),
                new TypeReference<List<CharacterListDto>>() {
                }
        );

        // 레벨 순 같으면 이름순으로 정렬
        characterList.sort(((o1, o2) -> {
            if (o2.getCharacter_level() == o1.getCharacter_level())
                return o1.getCharacter_name().compareTo(o2.getCharacter_name());
            else return o2.getCharacter_level() - o1.getCharacter_level();
        }));
        return characterList;
    }

    private static JsonNode getMyCharactersJSON(String apikey, String path, Map<String, String> params) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    if (params != null) params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .header("x-nxopen-api-key", apikey)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    /**
     * 캐릭터 기본 스탯 조회
     */
    public List<CharacterStatDto> getCharacterStat(String ocid) {
        mapper = new ObjectMapper();

        Map<String, String> params = new HashMap<>();
        params.put("ocid", ocid);

        // 캐릭터 기본 정보 가져오기
        JsonNode myCharactersJSON = getMyCharactersJSON(admin_api_key, "/maplestory/v1/character/stat", params);

        //json -> dto 변환
        return mapper.convertValue(
                myCharactersJSON.findValue("final_stat"),
                new TypeReference<ArrayList<CharacterStatDto>>() {
                }
        );
    }

}
