package edu.unidhaka.cse.cse2216.keephealthy;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class ProfileActivity extends Activity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private Button buttonDone;
    private Button birthDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginPage.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonDone = (Button) findViewById(R.id.buttonDone);
        birthDate = (Button) findViewById(R.id.birthdate);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());
        //test

        //adding listener to button

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackLogin();
            }
        });


        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage();

            }
        });

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();

            }
        });






    }

    private void BackLogin() {
        Intent intent = new Intent(this,LoginPage.class);
        startActivity(intent);
    }

    private void HomePage() {
        Intent intent = new Intent(this,home_page.class);
        startActivity(intent);
    }

    private void datePicker(){
        Intent intent = new Intent(this,date_picker.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}

