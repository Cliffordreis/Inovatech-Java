package com.Inovatech.Java.Inovatech.controller;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQTestController {

    @Autowired
    private ConnectionFactory connectionFactory;

    @GetMapping("/test-rabbitmq")
    public String testRabbitMQConnection() {
        try {
            connectionFactory.createConnection().close();
            return "Conexão com RabbitMQ funcionando!";
        } catch (Exception e) {
            return "Erro ao conectar ao RabbitMQ: " + e.getMessage();
        }
    }
}

