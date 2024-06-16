package damn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodConRepository {
    private Connection conn;

    public FoodConRepository(Connection conn) {
        this.conn = conn;
    }
    
    public Set<String> getAllType() {
        Set<String> allType = new HashSet<>();
        String sql = "SELECT DISTINCT type FROM FOODCON";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                allType.add(type);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoodConRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return allType;
    }
    
    public Set<String> getmainKind() {
        Set<String> mainKind = new HashSet<>();
        String sql = "SELECT DISTINCT main_kind FROM FOODCON";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String kind = rs.getString("main_kind");
                mainKind.add(kind);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoodConRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return mainKind;
    }
    
    public Map<Integer, FoodCon> getAllFoodCon() {
        Map<Integer, FoodCon> foodConMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM FOODCON")) {

            while (rs.next()) {
                int foodId = rs.getInt("food_id");
                String foodName = rs.getString("food_name");
                String type = rs.getString("type");
                String mainKind = rs.getString("main_kind");

                FoodCon foodCon = new FoodCon(foodId, foodName, type, mainKind);
                foodConMap.put(foodId, foodCon);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoodConRepository.class.getName()).log(Level.SEVERE, null, e);
        }

        return foodConMap;
    }
}

class FoodCon {
    private int foodId;
    private String foodName;
    private String type;
    private String mainKind;

    public FoodCon(int foodId, String foodName, String type, String mainKind) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.type = type;
        this.mainKind = mainKind;
    }

    //getter
    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getType() {
        return type;
    }

    public String getMainKind() {
        return mainKind;
    }
}