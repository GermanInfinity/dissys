import java.sql.*;
import org.apache.commons.dbcp2.*;

public class WeightsDao {

    private static BasicDataSource dataSource;

    public WeightsDao() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public void createWeightsEntry(Integer[] weights_entry) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {

            // INSERT INTO CONV1 TABLE
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO Conv1 (UserID, conv1)  VALUES (?,?)");
            Integer user_id = 1;
            Integer conv1 = 1;
            preparedStatement.setInt(user_id, weights_entry[0]);
            preparedStatement.setInt(conv1, weights_entry[1]);
            preparedStatement.executeUpdate();

            // INSERT INTO CONV2 TABLE
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO Conv2 (UserID, conv2)  VALUES (?,?)");
            Integer conv2 = 1;
            preparedStatement.setInt(user_id, weights_entry[0]);
            preparedStatement.setInt(conv2, weights_entry[2]);
            preparedStatement.executeUpdate();

            // INSERT INTO FC3 TABLE
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO FC3 (UserID, fc3)  VALUES (?,?)");
            Integer fc3 = 1;
            preparedStatement.setInt(user_id, weights_entry[0]);
            preparedStatement.setInt(fc3, weights_entry[3]);
            preparedStatement.executeUpdate();

            // INSERT INTO FC4 TABLE
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO FC4 (UserID, fc4)  VALUES (?,?)");
            Integer fc4 = 1;
            preparedStatement.setInt(user_id, weights_entry[0]);
            preparedStatement.setInt(fc4, weights_entry[4]);
            preparedStatement.executeUpdate();

            // INSERT INTO SOFTMAX TABLE
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO Softmax (UserID, smax)  VALUES (?,?)");
            Integer smax = 1;
            preparedStatement.setInt(user_id, weights_entry[0]);
            preparedStatement.setInt(smax, weights_entry[5]);
            preparedStatement.executeUpdate();

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
    }


}
