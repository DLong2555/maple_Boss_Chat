package project.maple.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.maple.domain.ApprovalStatus;

@Getter
public class PartyMemberDto {
    private Long id;
    private String charName;
    private String charOcid;
    private ApprovalStatus status;

    public PartyMemberDto(Long id, String charName, String charOcid, ApprovalStatus status) {
        this.id = id;
        this.charName = charName;
        this.charOcid = charOcid;
        this.status = status;
    }
}
