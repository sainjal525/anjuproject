package com.student_result.data.request_q_sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    private String exchange ="request.exchange";

    private String routingkey ="request.routingkey";

    public void send(String name) {
        rabbitTemplate.convertAndSend(exchange, routingkey, name);
        System.out.println("Sent RequestQ StudentName = " + name);
    }
}
