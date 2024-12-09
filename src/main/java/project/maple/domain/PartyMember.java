package project.maple.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PartyMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Party party;

    @ManyToOne
    private Member member;
}
