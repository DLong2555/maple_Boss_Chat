package project.maple.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinForm {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 필수입니다.")
    private String email;

    @NotBlank(message = "api-key는 필수입니다.")
    private String apiKey;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;


    public JoinForm() {
    }

    public JoinForm(String email, String password, String apiKey) {
        this.email = email;
        this.password = password;
        this.apiKey = apiKey;
    }
}
