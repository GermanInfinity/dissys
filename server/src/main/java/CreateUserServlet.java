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

@WebServlet(name = "CreateUserServlet", value = "/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
    private ConnectionFactory factory;

    public CreateUserServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/create_user.jsp");
        getServletContext().setAttribute("note", "Please input a username and password.");
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.isEmpty() || password.isEmpty())
        {
            RequestDispatcher req = request.getRequestDispatcher("/create_user.jsp");
            request.setAttribute("note", "Please enter appropriate username or password combination.");
            req.include(request, response);
        }
        else
        {
            LoginDao login_entry = new LoginDao();
            if (!login_entry.sign_up(username, password)) {
                RequestDispatcher req = request.getRequestDispatcher("/create_user.jsp");
                request.setAttribute("note", "User already exists");
                req.include(request, response);
            };

            // redirect to home page-- with no video names, can download combined or single
            // RequestDispatcher req = request.getRequestDispatcher("/index.jsp");
            // req.forward(request, response);
        }
    }
}