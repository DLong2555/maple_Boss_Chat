package project.maple.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    private String username;

    @NotNull(message = "비밀번호: 필수 정보입니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,16}&")
    private String userPass;

    @NotNull(message = "API: 필수 정보입니다.")
    @Column(unique = true)
    private String apiKey;

//    @Enumerated(EnumType.STRING)
//    private MemberRole role;
    private String role;

    public Member(String username, String userPass, String apiKey) {
        this.id = id;
        this.username = username;
        this.userPass = userPass;
        this.apiKey = apiKey;
        this.role = "ROLE_USER";
    }

    /*
        id만 사용한 멤버 생성자
         */
    public Member(Long id) {
        this.id = id;
    }
}
