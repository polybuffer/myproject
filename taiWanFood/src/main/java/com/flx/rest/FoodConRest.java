package com.flx.rest;

import com.flx.dao.FoodConRepository;
import com.flx.po.FoodCon;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@RestController
@RequestMapping("FoodConRest")
public class FoodConRest {

    @RequestMapping("getFoodConList")
    public List<FoodCon>  getFoodConList(Integer query) {
        List<FoodCon> fooConList = new ArrayList<>();
        FoodConRepository foodConRepository = new FoodConRepository();
        Map<Integer, FoodCon> foodConMap = foodConRepository.getAllFoodCon();

        Set<Integer> keySet = foodConMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()){
            Integer key = iterator.next();
            FoodCon foodCon = foodConMap.get(key);
            fooConList.add(foodCon);
        }

        return fooConList;
    }

}
