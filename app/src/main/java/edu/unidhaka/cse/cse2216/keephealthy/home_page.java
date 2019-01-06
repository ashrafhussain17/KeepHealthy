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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalorieTaken = "0 ";
        WaterTaken = "0 ";
        SleepHours = "0 ";
        CalorieBurnt = "0 ";

        mCalorieTaken = (TextView) findViewById(R.id.calorie_taken);
        mWaterTaken = (TextView) findViewById(R.id.water_taken);
        mSleepHours = (TextView) findViewById(R.id.sleep_hours);
        mCalorieBurnt = (TextView) findViewById(R.id.calorie_burnt);
    }

}