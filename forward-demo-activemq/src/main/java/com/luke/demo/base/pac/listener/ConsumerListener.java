package com.luke.demo.base.pac.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yangf on 2017/5/25.
 */
public class ConsumerListener{

    private final static String TCP_URL = "tcp://11.240.91.160:61616";

    public static void main(String[] args) throws Exception {
        //创建activemq连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(TCP_URL);


        // 从工厂创建一个链接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // Session，AUTO_ACKNOWLEDGE自动确认接收到消息
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //定义要接收消息的队列名称
        Destination destination = session.createQueue("myQueues.test");
        //构造消息消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //系统提供的监听方法
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //自己实现的监听
//        while (true) {
//            //消费消息,可以添加入参设置超时时间，不设置会一直等待；
//            Message message = consumer.receive();
//            TextMessage textMessage = (TextMessage) message;
//            String text = textMessage.getText();
//            System.out.println("Received: " + text);
//        }

    }
}
