package edu.unidhaka.cse.cse2216.keephealthy;

import java.util.Date;

public class CalorieCalculation {
    public static int mealAmount;
    public static int mealRAmount;
    public static int burntAmount;
    public static int burnRAmount;
    public static int sleepAmount;
    public static int sleepRAmount;
    public static int waterAmount;
    public static int waterRAmount;

    public static int currentDate;
    Date date;

    public CalorieCalculation(){
        currentDate = 0;

        mealAmount = 0;
        mealRAmount = 2000;
        burnRAmount = 1200;
        burntAmount = 0;
        sleepAmount = 0;
        sleepRAmount = 6;
        waterAmount = 0;
        waterRAmount = 2000;
    }

    public static void Check(){

    }
}
