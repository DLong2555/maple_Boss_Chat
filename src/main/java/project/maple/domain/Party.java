package project.maple.domain;

import jakarta.persistence.*;
import lombok.Getter;

//보스/난이도/파티명/파티장/인원/구인상태
@Entity
@Getter
public class Party {

    @Id @GeneratedValue
    private Long id;

    /**
     * 리더 캐릭 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String charName;

    private String partyName; //파티명 또는 파티 설명
    private String boss;
    private Difficulty difficulty; // 난이도
    private int currentMemberCount; // 현재 모집된 인원수
    private int maxMemberCount; // 최대 모집 인원수
    private PartyState partyState; // 모집 상태
}
