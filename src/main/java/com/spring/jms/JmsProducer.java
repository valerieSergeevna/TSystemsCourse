package com.spring.jms;

import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Component
public class JmsProducer {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("notificationsQueue")
    String queue;

    public JmsProducer() {
    }

    public void send(String info){
      //  jmsTemplate.convertAndSend(queue, info);
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage jmsMessage =
                        session.createTextMessage(info);
                return jmsMessage;
            }
        });
    }
}



//@Component
//public class JmsProducer {
//
//    private static final String JMS_CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
//    private static final String JMS_QUEUE_JNDI = "java:jboss/exported/jms/queue/TestQueue";
//    private static final String WILDFLY_REMOTING_URL = "http-remoting://127.0.0.1:8080";
//    private static final String JMS_USERNAME = "user1";
//    private static final String JMS_PASS = "user1";
//
//    public void send(String message) throws NamingException {
//        Properties props = new Properties();
//        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        props.setProperty(Context.PROVIDER_URL, "http-remoting://127.0.0.1:9090");
//       props.setProperty(Context.SECURITY_PRINCIPAL, "user1");
//       props.setProperty(Context.SECURITY_CREDENTIALS, "user1");
//       props.setProperty("remote.clusters", "ejb");
//
//       // props.put ("org.jboss.ejb.client.scoped.context", "true");
//
////        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
////        props.put("java.naming.provider")
//        Context context = new InitialContext(props);
//
//
//        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(JMS_CONNECTION_FACTORY_JNDI);
//        Queue queue = (Queue) context.lookup(JMS_QUEUE_JNDI);
//
//
//
//        try(QueueConnection connection = connectionFactory.createQueueConnection(JMS_USERNAME, JMS_PASS);
//            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//            QueueSender sender = session.createSender(queue)) {
//            connection.start();
//            TextMessage textMessage = session.createTextMessage(message);
//            sender.send(textMessage);
//
//        } catch ( JMSException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}