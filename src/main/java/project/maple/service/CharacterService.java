package project.maple.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import project.maple.dto.character.CharacterListDto;
import project.maple.dto.character.CharacterStatDto;
import project.maple.dto.character.item.ItemEquipmentDto;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
    @Cacheable(cacheNames = "getCharacters", key = "#username", cacheManager = "charactersCacheManager")
    public Map<String,List<CharacterListDto>> getMyCharacters(String username, String apiKey) throws JsonProcessingException {

        // Json으로 내 캐릭터 리스트 가져오기
        JsonNode myCharacters = getMyCharactersJSON(apiKey, "/maplestory/v1/character/list", null);

        HashMap<String, List<CharacterListDto>> myCharMap = new HashMap<>();

        // 내 캐릭터 리스트 json -> CharacterListDto
        List<CharacterListDto> characterListDtos = getCharacterListDtos(myCharacters);

        // CompletableFuture로 비동기 처리
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (CharacterListDto characterListDto : characterListDtos) {
            if(myCharMap.get(characterListDto.getWorld_name()) == null) {
                myCharMap.put(characterListDto.getWorld_name(), new ArrayList<CharacterListDto>());
            }

            Map<String, String> params = new HashMap<>();
            params.put("ocid", characterListDto.getOcid());

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    JsonNode charBasicData = getMyCharactersJSON(apiKey, "/maplestory/v1/character/basic", params);

                    characterListDto.setCharacter_guild_name(charBasicData.get("character_guild_name").asText());
                    characterListDto.setCharacter_image(charBasicData.get("character_image").asText());

                } catch (Exception e) {
                    log.info("No data found for character: {}", characterListDto.getCharacter_name());
                    log.error("No data found for ocid: {}", characterListDto.getOcid());
                    log.error(e.getMessage());
                }finally {
                    synchronized (myCharMap) {  // 동기화하여 ConcurrentModificationException 방지
                        myCharMap.get(characterListDto.getWorld_name()).add(characterListDto);
                    }
                }
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return myCharMap;
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
//        characterList.sort(((o1, o2) -> {
//            if (o2.getCharacter_level() == o1.getCharacter_level())
//                return o1.getCharacter_name().compareTo(o2.getCharacter_name());
//            else return o2.getCharacter_level() - o1.getCharacter_level();
//        }));
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

    /**
     * 캐릭터 장비 조회
     */
    public Map<String, List<ItemEquipmentDto>> getCharacterEquipment(String ocid) throws IOException {
        mapper = new ObjectMapper();

        Map<String, String> params = new HashMap<>();
        params.put("ocid", ocid);

        JsonNode myCharactersJSON = getMyCharactersJSON(admin_api_key, "/maplestory/v1/character/item-equipment", params);
        Map<String, List<ItemEquipmentDto>> itemEquipmentMap = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            String preset = "item_equipment_preset_" + i;
            ArrayList<ItemEquipmentDto> itemEquipmentPreset = mapper.convertValue(
                    myCharactersJSON.findValue(preset),
                    new TypeReference<ArrayList<ItemEquipmentDto>>() {
                    }
            );
            itemEquipmentMap.put(preset, itemEquipmentPreset);
        }


        return itemEquipmentMap;
    }


}
