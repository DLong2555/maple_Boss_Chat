package project.maple.dto.character.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemExceptionalOption extends ItemTotalOption{
    private int exceptional_upgrade;
}
