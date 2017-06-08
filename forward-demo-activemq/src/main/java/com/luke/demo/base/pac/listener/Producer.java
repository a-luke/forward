package com.luke.demo.base.pac.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yangf on 2017/5/25.
 */
public class Producer {

    private final static String TCP_URL = "tcp://11.240.91.160:61616";

    public static void main(String[] args) throws Exception {
        //创建mq连接
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(TCP_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //创建session，设置模式为自动答复
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建要监听的q
        Destination destination = session.createQueue("myQueues.test");
        MessageProducer messageProducer = session.createProducer(destination);

        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName();
        TextMessage message = session.createTextMessage(text);


        for (int i = 0; i < 10; i++){
            messageProducer.send(message);
            // Tell the producer to send the message
            System.out.println("Sent message: "+ text);
            Thread.sleep(2000);
        }

        messageProducer.close();
        session.close();
        connection.close();
    }
}
