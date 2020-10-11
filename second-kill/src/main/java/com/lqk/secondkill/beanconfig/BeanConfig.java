package com.lqk.secondkill.beanconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lqk
 * @Date 2020/9/20
 * @Description
 */
@Configuration
public class BeanConfig {

    @Bean
    public Queue secondKillQueue(){
        Queue secondKillQueue = new Queue("SecondKillQueue", true, false, false);
        return secondKillQueue;
    }

    @Bean
    public DirectExchange secondKillExchange(){
        DirectExchange secondKillExchange = new DirectExchange("SecondKillExchange", true, false);
        return secondKillExchange;
    }

    @Bean
    public Binding secondBinding(){
        return BindingBuilder.bind(secondKillQueue()).to(secondKillExchange()).with("SecondKillRouting");
    }

}
