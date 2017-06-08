package com.luke.demo.base.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yangf on 2017/5/25.
 */
public class TopicSubscriber {

    private final static String TCP_URL = "tcp://11.240.91.160:61616";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(TCP_URL);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("myTopic.messages");

        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println(tm.getJMSMessageID());
                    System.out.println("Received message: " + tm.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

//              session.close();
//              connection.stop();
//              connection.close();
    }
}
