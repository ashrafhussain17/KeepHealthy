package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends Activity {
    //aghsdghhga

    Button OKButton;
    EditText emailBox;
    EditText passwordBox;
    Button RegiButton;

    private FirebaseAuth RegistrationAuthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailBox = findViewById(R.id.email_field);
        passwordBox = findViewById(R.id.passwordField);
        OKButton = findViewById(R.id.okButton);
        RegiButton = findViewById(R.id.Regi);

        RegistrationAuthe = FirebaseAuth.getInstance();

        RegiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Registration.class));
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login(){
        RegistrationAuthe.signInWithEmailAndPassword(emailBox.getText().toString().trim(),passwordBox.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginPage.this,"Successful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
