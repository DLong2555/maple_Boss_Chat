package project.maple.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.maple.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void Member_valid_test() throws Exception {
        //given
        String id_not_email = "maxol2558";
        String id= "maxol2558@gmail.com";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        Member member_null_id = new Member(null, password, apiKey); // 아이디 없을 시
        Member member_null_pwd = new Member(id, null, apiKey); // 비밀번호 없을 시
        Member member_null_api = new Member(id, password, null); // api 없을 시
        Member member_not_emailId = new Member(id_not_email, password, apiKey); // 이메일 형식이 맞지 않을 시

        //when

        //then
        // 아이디가 null인 경우 예외 발생
        assertThrows(ConstraintViolationException.class, () -> memberRepository.saveAndFlush(member_null_id), "아이디는 필수 값입니다.");

        // 비밀번호가 null인 경우 예외 발생
        assertThrows(ConstraintViolationException.class, () -> memberRepository.saveAndFlush(member_null_pwd), "비밀번호는 필수 값입니다.");

        // apiKey가 null인 경우 예외 발생
        assertThrows(ConstraintViolationException.class, () -> memberRepository.saveAndFlush(member_null_api), "API 키는 필수 값입니다.");

        // 이메일 형식이 아닌 경우 예외 발생
        assertThrows(ConstraintViolationException.class, () -> memberRepository.saveAndFlush(member_not_emailId), "이메일 형식이 올바르지 않습니다.");
    }

}