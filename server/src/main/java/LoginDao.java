import java.sql.*;
import org.apache.commons.dbcp2.*;

public class LoginDao {

    private static BasicDataSource dataSource;

    public LoginDao() {
        dataSource = DBCPLoginDataSource.getDataSource();
    }

    public Boolean sign_up(String username, String password) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {

            //Insert user in dB
            String user_entry = "INSERT INTO user_table (Username, Password) VALUES (?, ?)";

            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(user_entry);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            int value = preparedStatement.executeUpdate();
            if (value == 0) { return false; }

            return true;

        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public Boolean has_signed_up(String username, String password) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {

            //Search for user in dB
            String user_search = "SELECT 1 FROM user_table WHERE (Username,Password)=(?,?)";

            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(user_search);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet result = preparedStatement.executeQuery();

            return is_user(result);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return false;
    }

    public Boolean is_user(ResultSet entry) throws SQLException {
        int size = 0;
        if (entry != null) {
            entry.last();
            size = entry.getRow();
        }
        if (size == 0) { return false; }
        return true;
    }


}
