import java.sql.*;
import org.apache.commons.dbcp2.*;

public class LiftRideDao {
    private static BasicDataSource dataSource;

    public LiftRideDao() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public void createLiftRide(Integer[] newLiftRide) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String insertQueryStatement = "INSERT INTO riders (liftRideID, skierID, resortID, seasonID, dayID, time, liftID) " +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(insertQueryStatement);
            Integer val_1 = 1;
            Integer val_2 = 2;
            Integer val_3 = 3;
            Integer val_4 = 4;
            Integer val_5 = 5;
            Integer val_6 = 6;
            Integer val_7 = 7;
            preparedStatement.setInt(val_1, newLiftRide[0]);
            preparedStatement.setInt(val_2, newLiftRide[1]);
            preparedStatement.setInt(val_3, newLiftRide[2]);
            preparedStatement.setInt(val_4, newLiftRide[3]);
            preparedStatement.setInt(val_5, newLiftRide[4]);
            preparedStatement.setInt(val_6, newLiftRide[5]);
            preparedStatement.setInt(val_7, newLiftRide[6]);

            // execute insert SQL statement
            preparedStatement.executeUpdate();
            System.out.println("Finish?");
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

    public static void main(String[] args) {
        LiftRideDao liftRideDao = new LiftRideDao();
        Integer[] lift_info = {10, 2, 3, 5, 500, 20, 8};
        liftRideDao.createLiftRide(lift_info);
    }
}