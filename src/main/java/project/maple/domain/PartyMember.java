package project.maple.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class PartyMember {

    @Id @GeneratedValue
    private Long id;

    /**
     * 파티원 정보
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String charName;
    private String charOcid;

    /**
     * 속한 파티
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    /*
    파티 승인 여부
     */
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    protected PartyMember() {}

    public PartyMember(Member member, String charName, String charOcid, Party party, ApprovalStatus status) {
        this.member = member;
        this.charName = charName;
        this.charOcid = charOcid;
        this.party = party;
        this.status = status;
    }

    public void approve() {
        this.status = ApprovalStatus.APPROVED;
    }

    public void reject() {
        this.status = ApprovalStatus.REJECTED;
    }
}
