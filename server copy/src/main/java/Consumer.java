import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.dbcp2.*;

import java.util.Date;

public class Consumer {
    private final static String QUEUE_NAME = "comms";
    private static ConcurrentHashMap<String, String> obj;

    public static void main(String[] argv) throws Exception {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost("34.199.20.210"); // ip address of rabbitmq, set username and password
        factory.setUsername("user4");
        factory.setPassword("pass");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //store_POJO obj = new store_POJO();
        obj = new ConcurrentHashMap<String, String>();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            obj.put(String.valueOf(java.time.LocalTime.now()), message);
            System.out.println(obj);
            String[] entries = {message.split("\\.")[0], message.split("\\.")[3]};

            Date date = new Date();
            long time_in_ms = date.getTime();
            Integer day = 1; // day varies for 3 runs
            Integer skierID = Integer.parseInt(entries[0]);
            Integer lift_rideID = Integer.parseInt(entries[1]);
            Integer liftID = Integer.parseInt(entries[1]);

            LiftRideDao liftRideDao = new LiftRideDao();
            //liftID, skierID, resortID, seasonID, dayID, time, liftID
            Integer[] lift_info = {lift_rideID, skierID, 1, 1, day, (int)time_in_ms, liftID}; // pass message into database here
            liftRideDao.createLiftRide(lift_info);

        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
