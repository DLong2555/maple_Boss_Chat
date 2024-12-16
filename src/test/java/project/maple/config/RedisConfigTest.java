package project.maple.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void redisTemplate_Test() throws Exception {
        //given
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String key = "name";
        ops.set(key, "John");
        String value = ops.get(key);

        //when

        //then
        assertEquals("John", value);
    }
}