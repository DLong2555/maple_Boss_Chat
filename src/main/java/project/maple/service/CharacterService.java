package project.maple.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import project.maple.domain.Member;
import project.maple.dto.CharacterListDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CharacterService {



    public List<CharacterListDto> getMyCharacters(Member member) throws JsonProcessingException {

        String baseUrl = "https://open.api.nexon.com";
        Map<String, String> header = new HashMap<>();
        header.put("x-nxopen-api-key", member.getApiKey());

        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

        String myCharacters = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/maplestory/v1/character/list")
                                .build()
                )
                .header("x-nxopen-api-key", member.getApiKey())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(myCharacters);

        List<CharacterListDto> characterList = new ArrayList<>();
        JsonNode accountListNodes = root.get("account_list");
        if(accountListNodes.isArray()) {
            for (JsonNode account : accountListNodes) {
                JsonNode characterNode = account.get("character_list");

                if (characterNode.isArray() && !characterNode.isEmpty()) {
                    for (JsonNode character : characterNode) {
                        String ocid = character.has("ocid") ? character.get("ocid").asText() : "";
                        String characterName = character.has("character_name") ? character.get("character_name").asText() : "Unknown";
                        String worldName = character.has("world_name") ? character.get("world_name").asText() : "Unknown";
                        String characterClass = character.has("character_class") ? character.get("character_class").asText() : "Unknown";
                        int characterLevel = character.has("character_level") ? character.get("character_level").asInt(0) : 0;

                        characterList.add(new CharacterListDto(ocid, characterName, worldName, characterClass, characterLevel));
                    }
                }
            }
        }


        return characterList;
    }

}
