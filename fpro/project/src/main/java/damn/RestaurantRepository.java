package damn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantRepository {
    private Connection conn;
    public RestaurantRepository(Connection conn) {
        this.conn = conn;
    }

    public Map<String, Restaurant> getAllRestaurants() {
        Map<String, Restaurant> restaurantMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANT")) {

            while (rs.next()) {
                //int id = rs.getInt("id");
                String name = rs.getString("name");
                double star = rs.getDouble("star");
                String food1 = rs.getString("food1");
                String food2 = rs.getString("food2");
                String food3 = rs.getString("food3");
                String local = rs.getString("local");
                String time = rs.getString("time");

                Restaurant restaurant = new Restaurant(name, star, food1, food2, food3, local, time);
                restaurantMap.put(name, restaurant);
            }

        } catch (SQLException e) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, e);
        }

        return restaurantMap;
    }
}
class Restaurant {
    private String name;
    private double star;
    private String food1;
    private String food2;
    private String food3;
    private String local;
    private String time;

    public Restaurant(String name, double star, String food1, String food2, String food3,
                    String local, String time) {
        this.name = name;
        this.star = star;
        this.food1 = food1;
        this.food2 = food2;
        this.food3 = food3;
        this.local =local;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public double getStar() {
        return star;
    }

    public String getFood1() {
        return food1;
    }

    public String getFood2() {
        return food2;
    }

    public String getFood3() {
        return food3;
    }

    public String getLocal() {
        return local;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", star=" + star +
                ", food1='" + food1 + '\'' +
                ", food2='" + food2 + '\'' +
                ", food3='" + food3 + '\'' +
                ", local='" + local + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}