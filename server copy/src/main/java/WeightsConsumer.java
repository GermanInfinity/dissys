import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.dbcp2.*;

import java.util.Date;

public class WeightsConsumer {
    private final static String QUEUE_NAME = "weight_comms";
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
            String[] entries = {message.split("\\.")[0], message.split("\\.")[3]};

            // plain/[user_id_num]/conv1/[num]/conv2/[num]/fc3/[num]/fc4/[num]/softmax/[num]
            Integer user_id_num = Integer.parseInt(entries[0]);
            Integer conv1 = Integer.parseInt(entries[1]);
            Integer conv2 = Integer.parseInt(entries[2]);
            Integer fc3 = Integer.parseInt(entries[3]);
            Integer fc4 = Integer.parseInt(entries[4]);
            Integer softmax = Integer.parseInt(entries[5]);

            WeightsDao weights_entry = new WeightsDao();
            Integer[] weights_info = {user_id_num,conv1,conv2,fc3,fc4,softmax};
            weights_entry.createWeightsEntry(weights_info);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
