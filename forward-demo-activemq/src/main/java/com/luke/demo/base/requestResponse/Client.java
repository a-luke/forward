package com.luke.demo.base.requestResponse;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yangf on 2017/5/25.
 */

public class Client implements MessageListener{
    private MessageProducer producer;
    private final static String TCP_URL = "tcp://11.240.91.160:61616";
    private Session session;

    private String clientName;

    private static String QUEUE_NAME = "request.response.queue";

    public static void main(String[] args) {
        new Thread(()-> {new Client("_1","client-1");}).start();
        new Thread(()->{new Client("_2","client-2");}).start();
        new Thread(()->{new Client("_3","client-3");}).start();
    }

   

    public Client(String clientMark, String clientName){
        this.clientName = clientName;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(TCP_URL);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination adminQueue = session.createQueue(QUEUE_NAME);

            //Setup a message producer to send message to the queue the server is consuming from
            this.producer = session.createProducer(adminQueue);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Create a temporary queue that this client will listen for responses on then create a consumer
            //that consumes message from this temporary queue...for a real application a client should reuse
            //the same temp queue for each message to the server...one temp queue per client
            Destination tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);

            //This class will handle the messages to the temp queue as well
            responseConsumer.setMessageListener(this);

            //Now create the actual message you want to send
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText(clientMark + "--MyProtocolMessage");

            //Set the reply to field to the temp queue you created above, this is the queue the server
            //will respond to
            txtMessage.setJMSReplyTo(tempDest);

            //Set a correlation ID so when you get a response you know which sent message the response is for
            //If there is never more than one outstanding message to the server then the
            //same correlation ID can be used for all the messages...if there is more than one outstanding
            //message to the server you would presumably want to associate the correlation ID with this
            //message somehow...a Map works good
            String correlationId = txtMessage.getJMSMessageID();
            txtMessage.setJMSCorrelationID(correlationId);

            while (true) {
                this.producer.send(txtMessage);
                System.out.println("send message : " + txtMessage.getText());
                Thread.sleep(3000);
            }


        } catch (Exception e) {
            e.printStackTrace();
            //Handle the exception appropriately
        }
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(clientName + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
