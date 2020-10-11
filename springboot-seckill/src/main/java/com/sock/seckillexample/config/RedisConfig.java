package com.sock.seckillexample.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-11 20:55
 **/
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redistpls(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> tpls = new RedisTemplate<>();

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        tpls.setConnectionFactory(factory);
        //key序列化方式
        tpls.setKeySerializer(redisSerializer);
        //value序列化
        tpls.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        tpls.setHashValueSerializer(jackson2JsonRedisSerializer);
        tpls.setEnableTransactionSupport(true);
        return tpls;
    }

}
