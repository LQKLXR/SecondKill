package com.lqk.secondkill.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lqk
 * @Date 2020/9/19
 * @Description 秒杀服务，提供多种策略的秒杀策略
 */
@Service
@Slf4j
public class SecondKillService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String PRE = "SECONDKILL:";
    private static final String SUF = ":STRING";
    private static final String GOOD = "GOOD";

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 秒杀-悲观锁版本
     */
    public void secondKillSynchronized(){
        synchronized (this){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            String key = PRE + GOOD + SUF;
            long left = redisTemplate.opsForValue().decrement(key);
            // 秒杀数量 -1
            if(left >= 0){
                log.debug("秒杀成功，当前剩余数量：" + left );
                rabbitTemplate.convertAndSend("SecondKillExchange", "SecondKillRouting", "订单系统下个订单");
            }
            else {
                redisTemplate.opsForValue().increment(key);
                log.debug("秒杀失败");
            }
        }
    }

    /**
     * 其实不需要阻塞
     */
    public void secondKillUnSync(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String key = PRE + GOOD + SUF;
        long left = redisTemplate.opsForValue().decrement(key);
        // 秒杀数量 -1
        if(left >= 0){
            int i = atomicInteger.incrementAndGet();
            log.debug("秒杀成功，当前剩余数量：" + left + "，共计成功线程数" + i);
            rabbitTemplate.convertAndSend("SecondKillExchange", "SecondKillRouting", "订单系统下个订单");
        }
        else {
            redisTemplate.opsForValue().increment(key);
            log.debug("秒杀失败");
        }
    }


    public void countRefresh(){
        atomicInteger.set(0);
    }
}
