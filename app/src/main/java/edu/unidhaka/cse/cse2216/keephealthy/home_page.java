package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class home_page extends Activity {


    public int calorie_taken = 0;
    public int water_taken = 0;
    public int sleep_hours = 0;
    public int calorie_burnt = 0;

    private Button buttonExercise;
    private Button buttonMeal;




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

        buttonExercise = (Button) findViewById(R.id.exercise);
        buttonMeal = (Button) findViewById(R.id.take_meal);

        buttonExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseList();
            }
        });

        buttonMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakingMeal();
            }
        });





    }

    private void TakingMeal() {
        Intent intent = new Intent(this,Take_a_meal.class);
        startActivity(intent);

    }

    private void ExerciseList() {
        Intent intent = new Intent(this,exercise.class);
        startActivity(intent);
    }

}