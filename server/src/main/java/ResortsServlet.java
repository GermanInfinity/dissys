import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


@WebServlet(name = "ResortServlet", value = "/ResortServlet")
public class ResortsServlet extends HttpServlet {
    private ConnectionFactory factory;
    private final static String QUEUE_NAME = "comms";

    public ResortsServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        long rec_time = System.currentTimeMillis();
        String urlPath = req.getPathInfo();

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
            QuerySkier q = new QuerySkier();
            String response = q.queryRiders(1, 1, 1, "resorts");
            res.getWriter().write("GET response query: " + response + "\n");

            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("It works now!");

        }
    }

    private boolean isUrlValid(String[] urlPath) {
        return true;
    }

}
