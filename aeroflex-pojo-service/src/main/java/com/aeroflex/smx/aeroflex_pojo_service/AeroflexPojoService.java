package com.aeroflex.smx.aeroflex_pojo_service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class AeroflexPojoService implements InitializingBean, DisposableBean {

    private static final Logger LOG = Logger.getLogger(AeroflexPojoService.class.getPackage().getName());

    private String user = ActiveMQConnection.DEFAULT_USER;

    private String password = ActiveMQConnection.DEFAULT_PASSWORD;

    private String url = "tcp://localhost:61616";

    private volatile boolean isShutdown;

    private Random random = new Random();

    private boolean transacted = false;

    private String subject = "aeroflex";

    private boolean persistent = false;

    private AeroflexPojoService aeroflexPojoService;

    public String sendMessage(String filename, String payload) {
        LOG.info("sending message from MainframePojoService");

        List<String> params = new ArrayList<String>();
        params.add(filename);
        params.add(payload);

        try {
            Connection connection = null;
            Destination destination = null;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            if (persistent) {
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            }

            int counter = 0;
            do {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                ObjectMessage message = session.createObjectMessage();
                // message.setObject("Message timestamp: " +
                // System.currentTimeMillis());
                LOG.info("sending message");

                message.setObject((Serializable) params);
                producer.send(message);
                counter++;
            } while (counter < 1);

            producer.close();
            session.close();
            connection.close();
        } catch (RuntimeException ex) {
            LOG.info("Error in send loop");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        LOG.info(".call Thread " + Thread.currentThread() + " done");

        return filename;

    }

    public void afterPropertiesSet() throws Exception {
        // logger.info("setting properties");
    }

    public void destroy() throws Exception {
        // logger.info("shutting down");
    }

}
