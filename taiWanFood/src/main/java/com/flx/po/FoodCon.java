package com.flx.po;

public class FoodCon {
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
