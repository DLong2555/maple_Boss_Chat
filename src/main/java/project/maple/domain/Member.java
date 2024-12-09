package project.maple.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String userEmail;
    private String userPass;

    private String apiKey;

    private String nowCharName;
}
