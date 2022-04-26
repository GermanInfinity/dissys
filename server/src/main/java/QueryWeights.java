import java.sql.*;
import org.apache.commons.dbcp2.*;

public class QueryWeights {
    private static BasicDataSource dataSource;

    public QueryWeights() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public String queryWeights(Integer value) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;

        String selectQueryStatement = "SELECT * from weights.Conv1 WHERE ID = ?";
        String selectQueryStatement1 = "SELECT * from weights.Conv2 WHERE ID = ?";
        String selectQueryStatement2 = "SELECT * from weights.FC3 WHERE ID = ?";
        String selectQueryStatement3 = "SELECT * from weights.FC4 WHERE ID = ?";
        String selectQueryStatement4 = "SELECT * from weights.Softmax WHERE ID = ?";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(selectQueryStatement);
            preparedStatement1 = conn.prepareStatement(selectQueryStatement1);
            preparedStatement2 = conn.prepareStatement(selectQueryStatement2);
            preparedStatement3 = conn.prepareStatement(selectQueryStatement3);
            preparedStatement4 = conn.prepareStatement(selectQueryStatement4);

            preparedStatement.setInt(1, value);
            preparedStatement1.setInt(1, value);
            preparedStatement2.setInt(1, value);
            preparedStatement3.setInt(1, value);
            preparedStatement4.setInt(1, value);

            // execute select from SQL
            ResultSet res = preparedStatement.executeQuery();
            ResultSet res1 = preparedStatement1.executeQuery();
            ResultSet res2 = preparedStatement2.executeQuery();
            ResultSet res3 = preparedStatement3.executeQuery();
            ResultSet res4 = preparedStatement4.executeQuery();
            res.next();
            return res.toString();

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
        return "0";
    }

}