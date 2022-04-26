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

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private ConnectionFactory factory;

    public LoginServlet() {
        this.factory = new ConnectionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, res);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.isEmpty() || password.isEmpty()) // Bad entry
        {
            RequestDispatcher req = request.getRequestDispatcher("/login.jsp");
            request.setAttribute("note", "Please enter appropriate username and password combination.");
            req.include(request, response);
        }
        else
        {
            LoginDao login_entry = new LoginDao();
            if (!login_entry.has_signed_up(username, password)) {
                RequestDispatcher req = request.getRequestDispatcher("/login.jsp");
                request.setAttribute("note", "Please sign up first");
                req.include(request, response);
            }

            // username
            // redirect to home page-- with video names, can download combined or single
            // RequestDispatcher req = request.getRequestDispatcher("/home.jsp");

            // Extract all videos for current user in database.
            VideosDAO usrs_videos_dao = new VideosDAO();
            Map<Integer, String> users_videos = new HashMap<Integer, String>();
            users_videos = usrs_videos_dao.get_video_filenames_for_user(username);

            // Call home for user with their videos
            RequestDispatcher req = request.getRequestDispatcher("/home.jsp");
            request.setAttribute("user", username);
            request.setAttribute("videos", users_videos);
            req.include(request, response);

        }

    }

}
