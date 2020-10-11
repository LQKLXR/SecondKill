package com.lqk.orderservice.listener;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lqk
 * @Date 2020/9/20
 * @Description
 */
@Component

public class OrderListener {


    @RabbitListener(queuesToDeclare = @Queue("SecondKillQueue"))
    public void handle(String message){
        System.out.println(message);
    }


}
