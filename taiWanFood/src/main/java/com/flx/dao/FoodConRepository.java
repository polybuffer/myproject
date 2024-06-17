package com.flx.dao;



import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flx.po.FoodCon;
import com.flx.util.JdbcUtil;


public class FoodConRepository {
    private Connection conn;

    public FoodConRepository() {
        try{
            this.conn = JdbcUtil.getInstance().getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
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
    // In FoodConRepository.java
    public Set<String> getFoodNamesByTypeAndCategory(String foodType, String foodCategory) {
        Set<String> foodNames = new HashSet<>();
        String query = "SELECT food_name FROM FOODCON WHERE type = ? AND main_kind = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodType);
            stmt.setString(2, foodCategory);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                foodNames.add(rs.getString("food_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodNames;
    }

}
