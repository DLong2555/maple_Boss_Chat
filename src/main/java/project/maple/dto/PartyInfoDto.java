package project.maple.dto;

import lombok.Getter;
import project.maple.domain.Difficulty;
import project.maple.domain.Party;
import project.maple.domain.PartyState;

import java.util.List;

@Getter
public class PartyInfoDto {

    private Long id;
    private String partyName;
    private String boss;
    private Difficulty difficulty;

    private int currentMemberCount;
    private List<String> applicants; // 신청자 리스트

    private int maxMemberCount;
    private List<String> members; // 승인된 파티원 리스트

    private PartyState partyState;

    public PartyInfoDto(Long id, String partyName, String boss, Difficulty difficulty, List<String> applicants, int maxMemberCount, List<String> members, PartyState partyState) {
        this.id = id;
        this.partyName = partyName;
        this.boss = boss;
        this.difficulty = difficulty;
        this.currentMemberCount = members != null ? members.size() : 0;
        this.applicants = applicants;
        this.maxMemberCount = maxMemberCount;
        this.members = members;
        this.partyState = partyState;
    }

}
