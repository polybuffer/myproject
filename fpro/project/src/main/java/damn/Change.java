package damn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Change {
    private Connection conn;

    public Change(Connection conn) {
        this.conn = conn;
    }
    // insert
    public void insertRestaurant(int restaurantId, String name, double star, String food1, String food2, String food3,
                                String local, String time) {

        String sql = "INSERT INTO RESTAURANT (id, name, star, food1, food2, food3, local, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            pstmt.setString(2, name);
            pstmt.setDouble(3, star);
            pstmt.setString(4, food1);
            pstmt.setString(5, food2);
            pstmt.setString(6, food3);
            pstmt.setString(7, local);
            pstmt.setString(8, time);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(Change.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertFoodCon(int food_id, String food_name, String type, String kind) {
        String sql = "INSERT INTO FOODCON (food_id, food_name, type, main_kind) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, food_id);
            pstmt.setString(2, food_name);
            pstmt.setString(3, type);
            pstmt.setString(4,kind);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Change.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
