package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends Activity {

    //defining view objects

    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    TextInputEditText passwordEditText, emailEdittext;
    TextInputLayout passwordTextInput;
    MaterialButton buttonSignup;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //initializing views
        emailEdittext = findViewById(R.id.email_edit_text);
        passwordTextInput = findViewById(R.id.password_text_input);
        passwordEditText = findViewById(R.id.password_edit_text);
        buttonSignup = findViewById(R.id.sign_up);

        progressDialog = new ProgressDialog(this);


    }

    private void registerUser() {

        //getting email and password from edit texts
        String email = emailEdittext.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            passwordTextInput.setError(getString(R.string.enter_email));
            //Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextInput.setError(getString(R.string.enter_password));
            //Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            //display some message here
                            Toast.makeText(Registration.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

//        @Override
//        public void onClick(View view) {
//
//            if(view == buttonSignup){
//                registerUser();
//            }
//
//            if(view == textViewSignin){
//                //open login activity when user taps on the already registered textview
//                startActivity(new Intent(this, LoginPage.class));
//            }
//
//        }


    public void click(View view) {
        if (view == buttonSignup) {
            registerUser();
        }
        startActivity(new Intent(this, ProfileActivity.class));


    }
}