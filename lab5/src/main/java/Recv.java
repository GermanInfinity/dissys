import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;


public class Recv {

    private final static String QUEUE_NAME = "hello";
    private static ConcurrentHashMap<String, String> obj;

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("35.174.138.163"); // ip address of rabbitmq, set username and password
        factory.setUsername("user3");
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
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}