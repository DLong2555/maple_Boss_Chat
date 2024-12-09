package project.maple.domain;

import jakarta.persistence.*;
import lombok.Getter;

//보스/난이도/파티명/파티장/인원/구인상태
@Entity
@Getter
public class Party {
    @Id @GeneratedValue
    private Long id;

    private Difficulty difficulty;
    private String partyName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member leader;

    private int maxMembers;
    private PartyState partyState;
}
