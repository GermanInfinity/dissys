import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    private ConnectionFactory factory;
    private final static String QUEUE_NAME = "comms";

    public SkierServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        long rec_time = System.currentTimeMillis();
        String urlPath = req.getPathInfo();
        System.out.println("HELLLO");
        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing parameters");
            return;
        }
        String[] urlParts = urlPath.split("/");

        if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {

            if (urlParts[urlParts.length - 1].equals("vertical")) {
                QuerySkier q = new QuerySkier();
                int skierID = Integer.parseInt(urlParts[1]);
                String response = q.queryRidersVertical(skierID);
                res.getWriter().write("GET response query: " + response + "\n");
            }

            else {
                QuerySkier q = new QuerySkier();
                int skierID = Integer.parseInt(urlParts[1]);
                int seasonID = Integer.parseInt(urlParts[5]);
                int dayID = Integer.parseInt(urlParts[7]);
                String response = q.queryRiders(skierID, seasonID, dayID, "riders");
                res.getWriter().write("GET response query: " + response + "\n");
            }

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
            BufferedReader req_body = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while( (line = req_body.readLine()) != null) {
                body.append(line);
            }
            System.out.println(body);
            res.getWriter().write("Post received!");
        }
        // use send class to queue in rabbitMQ on ec2 server
        // server is publisher
        // use post to publish to queue
        // put connection in constructor\
        String message = urlParts[1] + "." + urlParts[3] + "." + urlParts[5] + "." + urlParts[7];
        send(message); // to keep connection open,asynch, remove try

        // create 256 threaded post requests to server, alter url path to change entries into database

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

        if ((urlPath[0].equals("skiers") == false) || (urlPath[0].equals("resorts") == false)) {
            return false;
        }
        if (urlPath.length != 8) { return false; }
        if (isNumeric(urlPath[1]) == false) { return false; };
        if (isNumeric(urlPath[3]) == false) { return false; };
        if (isNumeric(urlPath[5]) == false) { return false; };
        if (isNumeric(urlPath[7]) == false) { return false; };
        if (isAlpha(urlPath[2]) == false) { return false; };
        if (isAlpha(urlPath[4]) == false) { return false; };
        if (isAlpha(urlPath[6]) == false) { return false; };
        return true;
    }

    private boolean isUrlValid(String[] urlPath) {
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
