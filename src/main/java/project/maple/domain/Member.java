package project.maple.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;

    @NotNull(message = "아이디: 필수 정보입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @Column(unique = true)
    private String userEmail;

    @NotNull(message = "비밀번호: 필수 정보입니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,16}&")
    private String userPass;

    @NotNull(message = "API: 필수 정보입니다.")
    @Column(unique = true)
    private String apiKey;

    private String nowCharName;

    public Member(String userEmail, String userPass, String apiKey) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.apiKey = apiKey;
    }
}
