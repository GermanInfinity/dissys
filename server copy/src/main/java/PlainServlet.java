import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@WebServlet(name = "PlainServlet", value = "/PlainServlet")
public class PlainServlet extends HttpServlet {
    private ConnectionFactory factory;

    public PlainServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        long rec_time = System.currentTimeMillis();
        String urlPath = req.getPathInfo();
        System.out.println("LOOOOL");
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
            System.out.println("Wrong entry");
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
