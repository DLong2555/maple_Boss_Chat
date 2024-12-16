package project.maple.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import project.maple.domain.Member;
import project.maple.dto.CharacterListDto;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CharacterService {

    static String baseUrl = "https://open.api.nexon.com";

    /**
     * 캐릭터 리스트 조회
     */
    @Cacheable(cacheNames = "getCharacters", key = "'character_list'", cacheManager = "charactersCacheManager")
    public List<CharacterListDto> getMyCharacters(Member member) throws JsonProcessingException {

        // Json으로 내 캐릭터 리스트 가져오기
        JsonNode myCharacters = getMyCharactersJSON(member, "/maplestory/v1/character/list", null);

        // 내 캐릭터 리스트 json -> CharacterListDto

        return getCharacterListDtos(myCharacters);
    }

    public List<CharacterListDto> getCharacterListDtos(JsonNode myCharacters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = myCharacters;

        List<CharacterListDto> characterList = new ArrayList<>();
        JsonNode accountListNodes = root.get("account_list");

        // dto로 파싱
        if(accountListNodes.isArray()) {
            for (JsonNode account : accountListNodes) {
                JsonNode characterNode = account.get("character_list");

                if (characterNode.isArray() && !characterNode.isEmpty()) {
                    for (JsonNode character : characterNode) {
                        CharacterListDto characterListDto = mapper.readValue(character.toString(), CharacterListDto.class);
                        characterList.add(characterListDto);
                    }
                }
            }
        }

        // 레벨 순 같으면 이름순으로 정렬
        characterList.sort(((o1, o2) -> {
            if(o2.getCharacter_level() == o1.getCharacter_level()) return o1.getCharacter_name().compareTo(o2.getCharacter_name());
            else return o2.getCharacter_level() - o1.getCharacter_level();
        }));
        return characterList;
    }

    private static JsonNode getMyCharactersJSON(Member member, String path, Map<String, String> params) {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    if (params != null) params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .header("x-nxopen-api-key", member.getApiKey())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

}
