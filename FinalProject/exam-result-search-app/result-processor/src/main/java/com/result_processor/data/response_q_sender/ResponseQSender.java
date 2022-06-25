package com.result_processor.data.response_q_sender;

import com.result_processor.data.repository.ResultsRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    ResultsRepository repository;

    String exchange ="response.exchange";
    String routingkey ="response.routingkey" ;

    public void send(String name) {
        System.out.println("Sent ResponseQStudent Result Deatils of Student");

        if(repository.existsById(name)) {

            String result = repository.findById(name).toString();
            rabbitTemplate.convertAndSend(exchange, routingkey, result);

        }
        else{

            String result = "No Student found with the Name: "+"'" + name +"'"+ " in the database";
            rabbitTemplate.convertAndSend(exchange, routingkey, result);
        }

    }
}
