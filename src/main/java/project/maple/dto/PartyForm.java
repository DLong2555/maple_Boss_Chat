package project.maple.dto;

import lombok.Getter;
import lombok.Setter;
import project.maple.domain.Difficulty;

@Getter @Setter
public class PartyForm {

    private String partyName;
    private String boss;
    private Difficulty difficulty;
    private int maxMemberCount;

    public PartyForm() {
    }

    public PartyForm(String partyName, String boss, Difficulty difficulty, int maxMemberCount) {
        this.partyName = partyName;
        this.boss = boss;
        this.difficulty = difficulty;
        this.maxMemberCount = maxMemberCount;
    }
}
