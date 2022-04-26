import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.*;

public class VideosDAO {

    private static BasicDataSource dataSource;

    public VideosDAO() {
        dataSource = DBCPVideoDataSource.getDataSource();
    }

    public Map<Integer, String> get_video_filenames_for_user(String username) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {

            // Search usernames in videos database, return all videos in result set
            String user_entry = "SELECT * FROM videos.video_table WHERE Username=(?)";

            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(user_entry);
            preparedStatement.setString(1,username);

            ArrayList<String> video_urls = new ArrayList<String>();
            Map<Integer, String> vid_urls = new HashMap<Integer, String>();
            ResultSet videos = preparedStatement.executeQuery();

            // get link in second column of table
            int c = 0;
            while (videos.next()) {
                vid_urls.put(c, videos.getString(2));
                c += 1;
            }
            return vid_urls;

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
        Map<Integer, String> garb = new HashMap<Integer, String>();
        return garb;
    }

}
