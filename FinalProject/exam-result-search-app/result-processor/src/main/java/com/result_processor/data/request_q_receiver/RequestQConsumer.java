package com.result_processor.data.request_q_receiver;

import com.result_processor.data.response_q_sender.ResponseQSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RequestQConsumer {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    ResponseQSender responseQSender;

    @RabbitListener(queues = "request.queue")
    public void recievedMessage(String name) {

        System.out.println("Received RequestQStudentName: "+ name);

        responseQSender.send(name);

    }
}
