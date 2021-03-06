package edu.unidhaka.cse.cse2216.keephealthy;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
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
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class ProfileActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private TextView nickname;
    private TextView height;
    private TextView weight;

    private EditText name;
    private Spinner sex;
    private EditText uheight;
    private EditText uweight;


    private DatabaseReference reference;


    private Button buttonLogout;
    private Button buttonDone;
    private TextView birthDate;
    private Button buttonSave;



    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    ImageButton mCaptureBtn;
    ImageView mImageView;

    Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference();

        buttonSave = (Button) findViewById(R.id.buttonSave);
        name = (EditText) findViewById(R.id.nickname);
        uheight = (EditText) findViewById(R.id.heightvalue);
        uweight = (EditText) findViewById(R.id.weightvalue);
        sex = (Spinner) findViewById(R.id.sex);

        final String spinner1 = sex.getSelectedItem().toString();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Users users = new Users(name.getText().toString(), spinner1,
                        uheight.getText().toString(),uweight.getText().toString());
                reference.child("room").push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Not Fulfilled", Toast.LENGTH_SHORT).show();
                    }
                });
                HomePage();

            }
        });



        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginPage.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        nickname = (TextView) findViewById(R.id.nickname);
        height = (TextView) findViewById(R.id.heightvalue);
        weight = (TextView) findViewById(R.id.weightvalue);

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonDone = (Button) findViewById(R.id.buttonDone);


        birthDate = (TextView) findViewById(R.id.birthdate);


        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });




        textViewUserEmail.setText("Welcome "+user.getEmail());


        buttonLogout.setOnClickListener(this);


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

    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginPage.class));
        }
    }

    private void HomePage() {

        String str1 = height.getText().toString();
        String str2 = weight.getText().toString();
        String str3 = nickname.getText().toString();

        if (!(TextUtils.isEmpty(str1))&& !(TextUtils.isEmpty(str2)) && !(TextUtils.isEmpty(str3) )){
            Intent intent = new Intent(this, home_page.class);
            startActivity(intent);
        }else {


            if (TextUtils.isEmpty(str1)) {
                height.setError("Please enter your weight");
                height.requestFocus();
            }

            if (TextUtils.isEmpty(str2)) {
                weight.setError("Please enter your height");
                weight.requestFocus();
            }

            if (TextUtils.isEmpty(str3)) {
                nickname.setError("Please enter your Nickname");
                nickname.requestFocus();
            }
        }

    }

    private void datePicker(){
        Intent intent = new Intent(this,date_picker.class);
        startActivity(intent);
    }


    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            mImageView.setImageURI(image_uri);
        }
    }


}

