package com.flx.rest;

import com.flx.dao.FoodConRepository;
import com.flx.dao.RestaurantRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@RestController
@RequestMapping("IndexRest")
public class IndexRest {


    @RequestMapping("getFoodType")
    public Set<String> getFoodType() {
        FoodConRepository foodConRepository = new FoodConRepository();
        Set<String> foodTypeList = foodConRepository.getAllType();
        return foodTypeList;
    }

    @RequestMapping("getFoodCategory")
    public Set<String> getFoodCategory() {
        FoodConRepository foodConRepository = new FoodConRepository();
        Set<String> foodCategoryList = foodConRepository.getmainKind();
        return foodCategoryList;
    }

    @RequestMapping("getFoodName")
    public Set<String> getFoodName(String foodType,String foodCategory) {
        FoodConRepository foodConRepository = new FoodConRepository();
        Set<String> foodNameList  = foodConRepository.getFoodNamesByTypeAndCategory(foodType, foodCategory);
        return foodNameList;
    }
}
