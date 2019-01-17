package edu.unidhaka.cse.cse2216.keephealthy;

import android.annotation.SuppressLint;
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
import android.text.TextWatcher;
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

    private TextView textViewSignup;

    TextInputEditText passwordEditText,emailEdittext;
    TextInputLayout passwordTextInput,emailTextInput;
    MaterialButton buttonSignIn;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        //initializing views
        emailEdittext = findViewById(R.id.email_edit_text);
        passwordTextInput = findViewById(R.id.password_text_input);
        emailTextInput= findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_edit_text);
        buttonSignIn = findViewById(R.id.sign_in);

        textViewSignup = (TextView) findViewById(R.id.signuplink);
        emailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailTextInput.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailTextInput.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordEditText.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        progressDialog = new ProgressDialog(this);

    }

    //method for user login
    private void userLogin() {

        String email = emailEdittext.getText().toString().trim();
        String password =  passwordEditText.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            emailTextInput.setError(getString(R.string.email_warning));
            //Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextInput.setError(getString(R.string.password_warning));
            //Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
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
            //userLogin();
            startActivity(new Intent(this,DrinkWater.class));
        }

        if (view == textViewSignup) {
            //finish();
            startActivity(new Intent(this, Take_a_meal.class));
        }
    }

}

