package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 11/15/17.
 */

public class Food {

    private String calories;
    private String fats;
    private String carbs;
    private String protein;
    private String BName;
    private String IName;
    //private int upc;


    public void setCalories (String calories) { this.calories = calories; }

    public String getCalories() { return calories; }

    public void setFats (String fats) { this.fats = fats; }

    public String getFats() { return fats; }

    public void setCarbs (String carbs) { this.carbs = carbs; }

    public String getCarbs () { return carbs; }

    public void setProtein (String protein) { this.protein = protein; }

    public String getProtein () { return protein; }

    public void setBName (String BName) { this.BName = BName; }

    public String getBName () { return BName; }

    public void setIName (String IName) { this.IName= IName; }

    public String getIName () { return IName; }

    //public void setUpc (int upc) { this.upc = upc; }

    //public int getUpc () { return upc; }

    @Override
    public String toString(){
        return BName + "\n" + calories;
    }


}