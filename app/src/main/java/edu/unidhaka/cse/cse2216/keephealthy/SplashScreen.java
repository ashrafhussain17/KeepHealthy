package edu.unidhaka.cse.cse2216.keephealthy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ImageView img;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        img= (ImageView)findViewById(R.id.image);
        tv= (TextView) findViewById(R.id.text);
        Animation mn= AnimationUtils.loadAnimation(this,R.anim.transition);
        img.startAnimation(mn);
        tv.startAnimation(mn);
        final Intent intent = new Intent(this,LoginPage.class);
        Thread thread = new Thread()
        {
            @Override
            public void run() {

                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
