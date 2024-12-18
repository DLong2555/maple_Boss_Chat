package project.maple.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import project.maple.dto.character.ChooseCharInfo;

@Getter @Setter
public class LoginSaveDto {

    @Email
    private String userEmail;
    private String apiKey;
    private String nowCharName;
    private String nowCharOcid;

    public LoginSaveDto(String userEmail, String apiKey) {
        this.userEmail = userEmail;
        this.apiKey = apiKey;
    }

    public void saveChooseCharInfo(ChooseCharInfo chooseCharInfo) {
        nowCharName = chooseCharInfo.getCharName();
        nowCharOcid = chooseCharInfo.getOcid();
    }
}
