package com.flx.rest;

import com.flx.dao.RestaurantRepository;
import com.flx.po.Restaurant;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("RestaurantRest")
public class RestaurantRest {

    @RequestMapping("getRestaurantList")
    public Set<Restaurant>  getRestaurantList(String foodName,String foodArea,String foodTime) {
        Set<Restaurant> restaurantSet = new HashSet<>();
        RestaurantRepository restaurantRepository = new RestaurantRepository();

        if(StringUtils.hasLength(foodName)&&StringUtils.hasLength(foodArea)&&StringUtils.hasLength(foodTime)){
            restaurantSet = restaurantRepository.getRestaurantNameByFoodNameAndTimeAndLocal(foodName,foodTime,foodArea);
        }else{
            Map<Integer, Restaurant> restaurantMap = restaurantRepository.getAllRestaurants();
            Set<Integer> keySet = restaurantMap.keySet();
            Iterator<Integer> iterator = keySet.iterator();
            while (iterator.hasNext()){
                Integer key = iterator.next();
                Restaurant restaurant = restaurantMap.get(key);
                restaurantSet.add(restaurant);
            }
        }
        return restaurantSet;
    }

}
