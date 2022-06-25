package com.student_result.data.student_controller;

import com.student_result.data.request_q_sender.RequestQSender;
import com.student_result.data.response_q_receiver.ResponseQConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    RequestQSender requestQSender;

    @Autowired
    ResponseQConsumer responseQConsumer;

    @GetMapping("/getresult")
    public String producer(@RequestParam String name) {

        requestQSender.send(name);

        while(responseQConsumer.isCheck() == false){
            System.out.print("");
//            *** don't skip the empty print statement above ***
        }

        responseQConsumer.setCheck(false);

        String result = responseQConsumer.getResult();

        if(result.charAt(0) == 'O'){
            result = result.substring(8,result.length());
            return result;
        }
        else{
            return result;
        }

    }

}

