package project.maple.dto.character.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemEquipmentDto {

    // 아이템 기본 정보
    private String item_equipment_part;
    private String item_name;
    private String item_icon;

    // 잠재능력
    private String potential_option_grade;
    private String potential_option_1;
    private String potential_option_2;
    private String potential_option_3;
    private String additional_potential_option_grade;
    private String additional_potential_option_1;
    private String additional_potential_option_2;
    private String additional_potential_option_3;

    // 주문서 작
    private String scroll_upgrade;

    // 스타포스
    private String starforce;

    // 아이템 종합 능력치
    @JsonProperty("item_total_option")
    private ItemTotalOption itemTotalOption;

    // 아이템 기본 능력치
    @JsonProperty("item_base_option")
    private ItemBaseOption itemBaseOption;

    // 아이템 추가 능력치
    @JsonProperty("item_add_option")
    private ItemAddOption itemAddOption;

    // 아이템 익셉셔널 능력치
    @JsonProperty("item_exceptional_option")
    private ItemExceptionalOption itemExceptionalOption;

    // 아이템 주문서 증가 능력치
    @JsonProperty("item_etc_option")
    private ItemEtcOption itemEtcOption;

    // 아이템 스타포스 증가 능력치
    @JsonProperty("item_starforce_option")
    private ItemStarforceOption itemStarforceOption;
}
