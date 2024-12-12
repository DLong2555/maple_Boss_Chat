package project.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CharacterListDto {

    @JsonProperty("ocid")
    private String ocid;

    @JsonProperty("character_name")
    private String character_name;

    @JsonProperty("world_name")
    private String world_name;

    @JsonProperty("character_class")
    private String character_class;

    @JsonProperty("character_level")
    private int character_level;

    public CharacterListDto(String ocid, String character_name, String world_name, String character_class, int character_level) {
        this.ocid = ocid;
        this.character_name = character_name;
        this.world_name = world_name;
        this.character_class = character_class;
        this.character_level = character_level;
    }
}
