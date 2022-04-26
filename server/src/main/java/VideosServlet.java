import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@WebServlet(name = "VideosServlet", value = "/VideosServlet")
public class VideosServlet extends HttpServlet {
    private ConnectionFactory factory;

    public VideosServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foo = request.getParameter("action");
        System.out.println(foo);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("http://127.0.0.1:5000/file_transfer");
        dispatcher.forward(request, response);

    }

}
