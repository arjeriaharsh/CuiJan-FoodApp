package com.harsharjeria.cuijan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView textView2;
    private String emailID;

    private FirebaseAuth auth;
    private Functions functions;

    private TextInputEditText passwordEditText;
    private FloatingActionButton floatingActionButton;

    private static String emailExtraIntent = "emailExtraIntent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        functions = new Functions();
        emailID = getIntent().getStringExtra(emailExtraIntent);
        auth = FirebaseAuth.getInstance();

        textView2 = findViewById(R.id.textView2);
        passwordEditText = findViewById(R.id.emailEditTextReal);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        textView2.setText("Logging In as\n" + emailID);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    private boolean validateData(){
        if (functions.isEmpty(passwordEditText)){
            passwordEditText.setError("Please enter a password.");
            return false;
        }
        return true;
    }

    private void signIn(){
        if(validateData()){
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
            progressDialog.setTitle("Logging in!");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString();
            auth.signInWithEmailAndPassword(emailID, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Intent startApp = new Intent(LoginActivity.this, HomePage.class);
                                startActivity(startApp);
                                CustomIntent.customType(LoginActivity.this, "left-to-right");
                                finishAffinity();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}
