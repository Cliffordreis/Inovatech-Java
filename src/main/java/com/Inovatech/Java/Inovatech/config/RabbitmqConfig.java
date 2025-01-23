package com.Inovatech.Java.Inovatech.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_NAME = "pedidoStatusQueue";

    @Bean
    public Queue pedidoStatusQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}

