package com.flx.po;

public class Restaurant {
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

}
