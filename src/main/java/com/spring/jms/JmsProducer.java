package com.spring.jms;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

//@Component
//public class JmsProducer {
//    @Autowired
//    JmsTemplate jmsTemplate;
//
//    @Value("TestQueue")
//    String queue;
//
//    public JmsProducer() {
//    }
//
//    public void send(JmsMessageTreatmentEvent info){
//        jmsTemplate.convertAndSend(queue, info);
//    }
//}

@Component
public class JmsProducer {

    private static final String JMS_CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
    private static final String JMS_QUEUE_JNDI = "jms/queue/TestQueue";
    private static final String WILDFLY_REMOTING_URL = "http-remoting://127.0.0.1:8080";
    private static final String JMS_USERNAME = "user1";
    private static final String JMS_PASS = "user1";

    public void send(String message) throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, WILDFLY_REMOTING_URL);
        props.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, JMS_PASS);
        props.put ("org.jboss.ejb.client.scoped.context", "true");

        Context context = new InitialContext(props);
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(JMS_CONNECTION_FACTORY_JNDI);
        Queue queue = (Queue) context.lookup(JMS_QUEUE_JNDI);

        try(QueueConnection connection = connectionFactory.createQueueConnection(JMS_USERNAME, JMS_PASS);
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue)) {
            connection.start();
            TextMessage textMessage = session.createTextMessage(message);
            sender.send(textMessage);

        } catch ( JMSException e) {
            e.printStackTrace();
        }

    }

}