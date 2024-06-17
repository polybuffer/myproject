package com.flx.dao;

import com.flx.po.Restaurant;
import com.flx.util.JdbcUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantRepository {
    private Connection conn;
    public RestaurantRepository() {
        try{
            this.conn = JdbcUtil.getInstance().getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Map<Integer, Restaurant> getAllRestaurants() {
        Map<Integer, Restaurant> restaurantMap = new HashMap<>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RESTAURANT");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double star = rs.getDouble("star");
                String food1 = rs.getString("food1");
                String food2 = rs.getString("food2");
                String food3 = rs.getString("food3");
                String local = rs.getString("local");
                String time = rs.getString("time");

                Restaurant restaurant = new Restaurant(name, star, food1, food2, food3, local, time);
                restaurantMap.put(id, restaurant);
            }

        } catch (SQLException e) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            JdbcUtil.getInstance().close(rs,stmt,conn);
        }

        return restaurantMap;

    }
    public Set<Restaurant> getRestaurantNameByFoodNameAndTimeAndLocal(String foodName, String time,String local) {
        Set<Restaurant> restaurantNames = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT WHERE (food1 = ? OR food2=? OR food3=?) AND time = ? AND local =?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodName);
            stmt.setString(2, foodName);
            stmt.setString(3, foodName);
            stmt.setString(4, time);
            stmt.setString(5, local);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double star = rs.getDouble("star");
                String food1 = rs.getString("food1");
                String food2 = rs.getString("food2");
                String food3 = rs.getString("food3");
                String localResult = rs.getString("local");
                String timeResult = rs.getString("time");

                Restaurant restaurant = new Restaurant(name, star, food1, food2, food3, localResult, timeResult);
                restaurantNames.add(restaurant);
            }

        } catch (SQLException e) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, e);
        }

        return restaurantNames;
    }
}
