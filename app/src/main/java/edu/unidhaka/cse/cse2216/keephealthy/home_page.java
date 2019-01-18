package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class home_page extends Activity {
    public int calorie_taken = 0;
    public int water_taken = 0;
    public int sleep_hours = 0;
    public int calorie_burnt = 0;

    public String CalorieTaken, WaterTaken, SleepHours, CalorieBurnt;

    TextView mCalorieTaken, mWaterTaken, mSleepHours, mCalorieBurnt;
    TextView mMealAmount, mWaterAmount, mSleepAmount, mBurntAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        CalorieTaken = "0 ";
        WaterTaken = "0 ";
        SleepHours = "0 ";
        CalorieBurnt = "0 ";

        mCalorieTaken = (TextView) findViewById(R.id.mealid);
        mWaterTaken = (TextView) findViewById(R.id.waterid);
        mSleepHours = (TextView) findViewById(R.id.sleepid);
        mCalorieBurnt = (TextView) findViewById(R.id.burntid);
        mBurntAmount=(TextView) findViewById(R.id.burntamount);
        mMealAmount=(TextView) findViewById(R.id.mealamount);
        mSleepAmount=(TextView) findViewById(R.id.sleepamount);
        mWaterAmount=(TextView) findViewById(R.id.wateramount);
    }

}