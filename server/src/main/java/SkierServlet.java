import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //res.setContentType("text/plain");
        long rec_time = System.currentTimeMillis();
        //System.out.println(rec_time);
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
            res.setStatus(HttpServletResponse.SC_OK);
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

        if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            BufferedReader req_body = req.getReader();
            StringBuilder body = new StringBuilder();
            String line;
            while( (line = req_body.readLine()) != null) {
                body.append(line);
            }
            System.out.println(body);
            //res.getWriter().write("Post sent!");
        }
    }
    private boolean isUrlValid(String[] urlPath) {
        // Validation: Length of UrlPaths, presence of integer and alphabet
        // at desired location
        if (urlPath.length != 8) { return false; };
        //if (urlPath[0] == '') { return false; };
        if (isNumeric(urlPath[1]) == false) { return false; };
        if (isNumeric(urlPath[3]) == false) { return false; };
        if (isNumeric(urlPath[5]) == false) { return false; };
        if (isNumeric(urlPath[7]) == false) { return false; };
        if (isAlpha(urlPath[2]) == false) { return false; };
        if (isAlpha(urlPath[4]) == false) { return false; };
        if (isAlpha(urlPath[6]) == false) { return false; };
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
