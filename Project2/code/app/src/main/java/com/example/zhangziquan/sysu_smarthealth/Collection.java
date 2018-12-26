package com.example.zhangziquan.sysu_smarthealth;

import java.io.Serializable;

public class Collection implements Serializable {
    private String food_name;
    private String food_type;
    private String food_nutrients;
    private String bg_color;
    private Boolean isCollected;
    private Boolean isFavor;

    public Collection(String name, String type, String nutrients, String color){
        food_name = name;
        food_type = type;
        food_nutrients = nutrients;
        bg_color = color;
        isCollected = false;
        isFavor = false;
    }

    public String getName() {
        return food_name;
    }

    public String getFirst() {
        return food_type.substring(0,1);
    }

    public String getFood_type() {
        return food_type;
    }

    public String getFood_nutrients() {
        return food_nutrients;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setFf(String name, String type, String nutrients, String color) {
        food_name = name;
        food_type = type;
        food_nutrients = nutrients;
        bg_color = color;
    }

    public boolean isCollected(){
        return isCollected;
    }

    public void setIsCollected(Boolean _isColleted){
        isCollected = _isColleted;
    }

    public Boolean getFavor() {
        return isFavor;
    }

    public void setIsFavor(Boolean _isFavor){
        isFavor = _isFavor;
    }
}
