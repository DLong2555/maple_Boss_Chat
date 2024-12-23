package project.maple.dto.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequestDto {

    private final boolean success;
    private final LoginSaveDto saveDto;

}
