package project.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CharacterListDto {

    private String ocid;
    private String character_name;
    private String world_name;
    private String character_class;
    private int character_level;

}
