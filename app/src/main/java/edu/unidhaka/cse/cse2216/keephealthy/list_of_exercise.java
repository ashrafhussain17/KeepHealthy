package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.concurrent.TimeUnit;

public class list_of_exercise extends Activity {

    private TextView tvtitle, tvdescription, tvcategory;
    private ImageView img;

    private static TextView countdownTimerText;
    private static EditText minutes;
    private static Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_exercise);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        img.setImageResource(image);


        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        startTimer = (Button) findViewById(R.id.startTimer);
        resetTimer = (Button) findViewById(R.id.resetTimer);

        setListeners();


    }

    private void setListeners() {
        //startTimer.setOnClickListener((View.OnClickListener) this);
        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTimer();
            }
        });


        //resetTimer.setOnClickListener((View.OnClickListener) this);

        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetTimer();
            }
        });
    }

    private void ResetTimer() {

        stopCountdown();//stop count down
        startTimer.setText(getString(R.string.start_timer));//Change text to Start Timer
        countdownTimerText.setText(getString(R.string.timer));

    }

    private void StartTimer() {
        if (countDownTimer == null) {
            String getMinutes = minutes.getText().toString();
            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                startTimer(noOfMinutes);//start countdown
                startTimer.setText(getString(R.string.stop_timer));//Change Text
            } else
                Toast.makeText(list_of_exercise.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
        } else {
            stopCountdown();
            startTimer.setText(getString(R.string.start_timer));
        }
    }


    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }
}
