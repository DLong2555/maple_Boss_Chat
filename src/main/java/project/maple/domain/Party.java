package project.maple.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "파티장 : 필수 정보입니다.")
    private Member member;
    private String charName;
    private String charOcid;

    @NotNull(message = "파티명 : 필수 정보입니다.")
    @Column(nullable = false)
    private String partyName; //파티명 또는 파티 설명

    @NotNull(message = "보스 : 필수 정보입니다.")
    @Column(nullable = false)
    private String boss;

    @NotNull(message = "난이도 : 필수 정보입니다.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty; // 난이도

    private int currentMemberCount = 0; // 현재 모집된 인원수

    @NotNull(message = "파티 모집 인원 : 필수 정보입니다.")
    @Column(nullable = false)
    private int maxMemberCount; // 최대 모집 인원수

    @NotNull(message = "파티상태 : 필수 정보입니다.")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartyState partyState; // 모집 상태

    protected Party() {}

    public Party(Member member, String charName, String charOcid, String partyName, String boss, Difficulty difficulty, int maxMemberCount) {
        this.member = member;
        this.charName = charName;
        this.charOcid = charOcid;
        this.partyName = partyName;
        this.boss = boss;
        this.difficulty = difficulty;
        this.maxMemberCount = maxMemberCount;
        this.partyState = PartyState.OPEN;
        this.currentMemberCount++;
    }

}
