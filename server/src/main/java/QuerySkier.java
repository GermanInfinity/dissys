import java.sql.*;
import org.apache.commons.dbcp2.*;

public class QuerySkier {
    private static BasicDataSource dataSource;

    public QuerySkier() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public String queryRiders(Integer skierID, Integer seasonID, Integer dayID, String dB) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        String selectQueryStatement = "SELECT COUNT(distinct( skierID)) AS skierCount FROM " + dB +
                                       " WHERE resortID = ? AND seasonID = ? AND dayID = ?;";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(selectQueryStatement);


            preparedStatement.setInt(1, skierID);
            preparedStatement.setInt(2, seasonID);
            preparedStatement.setInt(3, dayID);

            // execute select from SQL
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String count = Integer.toString(res.getInt("skierCount"));

            return count;

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

    public String queryRidersVertical(Integer skierID) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        String selectQueryStatement = "SELECT COUNT(distinct(skierID)) AS skierCount FROM riders  WHERE skierID = ? ";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(selectQueryStatement);

            preparedStatement.setInt(1, skierID);

            // execute select from SQL
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String count = Integer.toString(res.getInt("skierCount"));

            return count;

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