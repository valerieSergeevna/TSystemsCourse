
package com.spring.jms;
import org.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer {
    @JmsListener(destination = "jsa-queue", containerFactory="jsaFactory")
    public void receive(JmsMessageTreatmentEvent info){
        System.out.println("Recieved Message: " + info);
    }
}