package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
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

public class Registration extends Activity {

    //hello


    Button ROKButton;
    EditText RemailBox;
    EditText RpasswordBox;

    private FirebaseAuth registrationAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);


        RemailBox = findViewById(R.id.Remail_field);
        RpasswordBox = findViewById(R.id.RpasswordField);
        ROKButton = findViewById(R.id.RokButton);

        registrationAuth = FirebaseAuth.getInstance();

        ROKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
            }
        });

    }

    public void createUser(){
        registrationAuth.createUserWithEmailAndPassword(RemailBox.getText().toString().trim(),RpasswordBox.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this,"User Created",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }
}
