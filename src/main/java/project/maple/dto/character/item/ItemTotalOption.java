package project.maple.dto.character.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

@Getter @Setter
public class ItemTotalOption{

    private String str;
    private String dex;

    @JsonProperty("int")
    private String int_stat;

    private String luk;
    private String max_hp;
    private String max_mp;
    private String attack_power;
    private String magic_power;

    private String armor;
    private String speed;
    private String jump;
    private String boss_damage;
    private String all_stat;
    private String damage;
    private String equipment_level_decrease;

    private String ignore_monster_armor;
    private String max_hp_rate;
    private String max_mp_rate;

}
