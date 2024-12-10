package project.maple.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.maple.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void Member_valid_test() throws Exception {
        //given
//        String id = "maxol2558@gmail.com";
        String id = "";
        String password = "zkzktl25@#";
        String apiKey = "test_c0f890b5cf97d7fb50a6c7e198a0201737fa23a015b6aa1f1c950850400ce9eeefe8d04e6d233bd35cf2fabdeb93fb0d";
        Member member = new Member(id, password, apiKey);

        //when
        memberRepository.save(member);

        //then

    }

}