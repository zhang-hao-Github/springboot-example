package com.sock.seckillexample.controller;

import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-example
 * @author: @ZhangHao
 * @create: 2020-10-11 20:18
 **/

@RestController
public class SeckillController {

    @Autowired
    @Qualifier("redistpls")
    private RedisTemplate redisTemplate;


    /**
     * 秒杀随机率较高  非先到先得
     *
     * @description: watch
     * @author: hr
     * @date: 2020/10/11 23:09
     * @param: null
     * @return:
     */
    @GetMapping("seckill")
    public String seckill() {

        redisTemplate.watch("product");
        Integer kc = (Integer) redisTemplate.opsForValue().get("product");
        if (kc > 0) {
            redisTemplate.multi();
            redisTemplate.opsForValue().decrement("product", 1);
            List exec = redisTemplate.exec();
            if (null != exec && exec.size() > 0) {
                System.out.println("当前用户抢购到商品--" + kc + "+，已被抢购" + (100 - kc));

                //send 订单信息 到mq消费
            } else {
                System.out.println("当前用户抢购失败--" + kc);
            }
            return null;
        }
        return null;
    }

    /**
     * 利用setnx进行 秒杀
     * 取前100 先到先得 不断自旋
     *
     * @description:
     * @author: hr
     * @date: 2020/10/12 0:18
     * @param: null
     * @return:
     */
    boolean flag = false;

    @GetMapping("redisSetNx")
    public String redisSetNx() {
        if (flag = true) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("product:seckill:lock", uuid, 2, TimeUnit.SECONDS);
        if (aBoolean) {
            Integer kc = (Integer) redisTemplate.opsForValue().get("product");
            if (kc == 0) {
                if (uuid.equals(redisTemplate.opsForValue().get("product:seckill:lock"))) {
                    redisTemplate.delete("product:seckill:lock");
                }
                flag = true;
            }
            if (kc > 0) {
                redisTemplate.opsForValue().decrement("product", 1);
                System.out.println("当前用户" + Thread.currentThread().getName() + "抢购到商品--" + kc + "已被抢购" + (100 - kc));
                //send 订单信息 到mq消费
                if (uuid.equals(redisTemplate.opsForValue().get("product:seckill:lock"))) {
                    redisTemplate.delete("product:seckill:lock");
                }

            }
        } else {
            System.out.println("当前用户" + Thread.currentThread().getName() + "抢购失败商品");
            return redisSetNx();
        }

        return null;

    }

}
