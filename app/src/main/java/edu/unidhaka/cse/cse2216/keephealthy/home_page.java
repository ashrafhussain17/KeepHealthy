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

    private TextView buttonExercise;
    private TextView buttonMeal;

    private TextView drinkwater;




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

        buttonExercise = (TextView) findViewById(R.id.exerciseText);
        buttonMeal = (TextView) findViewById(R.id.mealText);
        drinkwater = (TextView) findViewById(R.id.drinktext);

        drinkwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drinkwater();
            }
        });

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

    private void Drinkwater() {
        Intent intent = new Intent(this,DrinkWater.class);
        startActivity(intent);
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