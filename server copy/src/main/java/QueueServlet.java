import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@WebServlet(name = "QueueServlet", value = "/QueueServlet")
public class QueueServlet extends HttpServlet {
    private ConnectionFactory factory;
    private final static String QUEUE_NAME = "weight_comms";

    public QueueServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        long rec_time = System.currentTimeMillis();
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing parameters");
            return;
        }
        String[] urlParts = urlPath.split("/");

        if (!isUrlValidGet(urlParts)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("It works now!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing parameters");
            return;
        }
        String[] urlParts = urlPath.split("/");

        if (!isUrlValidPost(urlParts)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            // plain/[user_id_num]/conv1/[num]/conv2/[num]/fc3/[num]/fc4/[num]/softmax/[num]
            WeightsDao weights_entry = new WeightsDao();
            Integer[] weights_info = {Integer.parseInt(urlParts[1]),Integer.parseInt(urlParts[3]),
                    Integer.parseInt(urlParts[5]),Integer.parseInt(urlParts[7]),
                    Integer.parseInt(urlParts[9]),Integer.parseInt(urlParts[11])};
            weights_entry.createWeightsEntry(weights_info);

            res.getWriter().write("Post received!");
        }

        String message = Integer.parseInt(urlParts[1]) + "." + Integer.parseInt(urlParts[3]) + "." +
                Integer.parseInt(urlParts[5]) + "." + Integer.parseInt(urlParts[7]) + "." +
                Integer.parseInt(urlParts[9]) + "." + Integer.parseInt(urlParts[11]);
        send(message);
    }

    private void send(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("34.199.20.210");
        factory.setUsername("user4");
        factory.setPassword("pass");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private boolean isUrlValidPost(String[] urlPath) {

        if ((urlPath[0].equals("plain") == false)) {
            return false;
        }
        if (urlPath.length != 12) { return false; }
        if (!isNumeric(urlPath[3])) { return false; };
        if (!isNumeric(urlPath[5])) { return false; };
        if (!isNumeric(urlPath[7])) { return false; };
        if (!isNumeric(urlPath[9])) { return false; };
        if (!isNumeric(urlPath[11])) { return false; };
        if (!Objects.equals(urlPath[2], "conv1")) { return false; };
        if (!Objects.equals(urlPath[4], "conv2")) { return false; };
        if (!Objects.equals(urlPath[6], "fc3")) { return false; };
        if (!Objects.equals(urlPath[9], "fc4")) { return false; };
        if (!Objects.equals(urlPath[10], "softmax")) { return false; };
        return true;
    }

    private boolean isUrlValidGet(String[] urlPath) {
        return true;
    }

    // Function determines if a string is all integers.
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    // Function determines if a string is all alphabets.
    public static boolean isAlpha(String s) {
        return s != null && s.chars().allMatch(Character::isLetter);
    }
}
