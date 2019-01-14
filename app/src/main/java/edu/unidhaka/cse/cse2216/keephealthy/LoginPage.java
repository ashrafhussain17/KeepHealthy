package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends Activity  {


    //defining views
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sakib","b4login");
        setContentView(R.layout.login);
        Log.d("sakib","after login");

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        Log.d("sakib","after firebase");
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.emailtext);
        editTextPassword = (EditText) findViewById(R.id.passwordtext);
        buttonSignIn = (Button) findViewById(R.id.signinBtn);
        textViewSignup = (TextView) findViewById(R.id.signuplink);

        progressDialog = new ProgressDialog(this);

        //attaching click listener

    }

    //method for user login
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });

    }

    public void click(View view)
    {
        if (view == buttonSignIn) {
        userLogin();
    }

        if (view == textViewSignup) {
            finish();
            startActivity(new Intent(this, Registration.class));
        }
    }
//    @Override
//    public void onClick(View view) {
//        Log.d("sakib","inside onclick");
//        if (view == buttonSignIn) {
//            userLogin();
//        }
//
//        if (view == textViewSignup) {
//            finish();
//            startActivity(new Intent(this, Registration.class));
//        }
//    }
}

