package edu.unidhaka.cse.cse2216.keephealthy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public int calorie_taken = 0;
    public int water_taken = 0;
    public int sleep_hours = 0;
    public int calorie_burnt = 0;

    public ProgressBar progressSleep,progressMeal,progressDrinks,progressBurnt;
    public String CalorieTaken, WaterTaken, SleepHours, CalorieBurnt;
    public TextView mMealAmount, mDrinksAmount, mSleepAmount, mBurntAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressSleep=(ProgressBar)findViewById(R.id.SleepProgress);
        progressMeal=(ProgressBar)findViewById(R.id.MealProgress);
        progressBurnt=(ProgressBar)findViewById(R.id.BurntProgress);
        progressDrinks=(ProgressBar)findViewById(R.id.DrinksProgress);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


        CalorieTaken = "0 ";
        WaterTaken = "0 ";
        SleepHours = "0 ";
        CalorieBurnt = "0 ";

        mBurntAmount = (TextView) findViewById(R.id.burntamount);
        mMealAmount = (TextView) findViewById(R.id.mealamount);
        mSleepAmount = (TextView) findViewById(R.id.sleepamount);
        mDrinksAmount = (TextView) findViewById(R.id.drinksamount);

        showProgress();

    }

    @SuppressLint({"NewApi", "SetTextI18n"})
    private void showProgress() {

        mBurntAmount.setText("25"+" / "+"100");
        mMealAmount.setText("25"+" / "+"100");
        mSleepAmount.setText("25"+" / "+"100");
        mDrinksAmount.setText("25"+" / "+"100");
        progressDrinks.setProgress(25,true);
        progressBurnt.setProgress(25,true);
        progressSleep.setProgress(25,true);
        progressMeal.setProgress(25,true);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Log.d("sakib","navigation");
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.nav_profile:
                intent = new Intent(this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                AlertDialog.Builder  builder= new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.logout_alert,null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view);
                builder.show();


                break;


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void clicked(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id) {
            case R.id.take_a_meal_id:
                intent = new Intent(this, Take_a_meal.class);
                startActivity(intent);
                break;

            case R.id.drink_water_id:
                intent = new Intent(this, DrinkWater.class);
                startActivity(intent);
                break;

            case R.id.exercise_id:
                intent = new Intent(this, exercise.class);
                startActivity(intent);
                break;

        }
    }


}