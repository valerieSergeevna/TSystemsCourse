package com.spring.jms;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("jsa-queue")
    String queue;

    public JmsProducer() {
    }

    public void send(JmsMessageTreatmentEvent info){
        jmsTemplate.convertAndSend(queue, info);
    }
}
