package com.student_result.data.response_q_receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class ResponseQConsumer {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    private boolean check =false;

    private String result;

    @RabbitListener(queues = "response.queue")
    public void recievedMessage(String student) {

        setResult(student);

        setCheck(true);

        System.out.println("Received the ResponseQStudent Details: ");

        System.out.println(student);

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
