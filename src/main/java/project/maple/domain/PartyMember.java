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
    private Member member;
    private String charName;

    /**
     * 속한 파티
     */
    @ManyToOne(fetch = LAZY)
    private Party party;

}
