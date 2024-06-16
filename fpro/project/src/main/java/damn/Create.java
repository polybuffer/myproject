package damn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Create {
    private Connection conn;

    public Create(Connection conn) {
        this.conn = conn;
    }

    // remove
    public void removeTable() {
        try(Statement stmt = conn.createStatement()) {
            String sql = "DROP TABLE IF EXISTS table_name";

            stmt.executeUpdate(sql);
            System.out.println("Successfully remove REGISTRATION");
        } catch (SQLException e) {
            Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // table 1
    public void table1() {
        try(Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE RESTAURANT" +
                        "(id INT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "star DECIMAL(2,1), " +
                        "food1 VARCHAR(255) NOT NULL, " +
                        "food2 VARCHAR(255), " +
                        "food3 VARCHAR(255), " +
                        "local VARCHAR(255), " +
                        "time VARCHAR(255))";

            stmt.executeUpdate(sql);
            System.out.println("Successfully create");
        } catch (SQLException e) {
            Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // table2
    public void table2() {
        try(Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE FOODCON ( " +
                        "food_id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "food_name VARCHAR(255) NOT NULL," +
                        "type VARCHAR(255), " +
                        "main_kind VARCHAR(255))";
            stmt.executeUpdate(sql);
            System.out.println("Successfully create table2");
        } catch (SQLException e) {
            Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
