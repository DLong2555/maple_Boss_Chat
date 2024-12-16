package project.maple.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.maple.domain.Member;

@RequiredArgsConstructor
@Getter
public class LoginRequestDto {

    private final boolean success;
    private final LoginSaveDto saveDto;

}
