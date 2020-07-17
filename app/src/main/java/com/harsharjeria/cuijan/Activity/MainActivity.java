package com.harsharjeria.cuijan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Functions functions;
    private FirebaseAuth auth;


    private FloatingActionButton loginOrRegisterFAB;

    private static String emailExtraIntent = "emailExtraIntent";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            Intent startApp = new Intent(MainActivity.this, HomePage.class);
            startActivity(startApp);
            finish();
        }

        functions = new Functions();
        loginOrRegisterFAB = findViewById(R.id.floatingActionButton);
        progressDialog = new ProgressDialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
        loginOrRegisterFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imClickedNext();
            }
        });



    }

    private void imClickedNext(){
        TextInputEditText editTextEmail = findViewById(R.id.emailEditTextReal);
        if(functions.isEmpty(editTextEmail)){
            editTextEmail.setError("Please enter an EmailID to continue.");
            return;
        }
        final String emailId = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        if(!functions.validateEmail(emailId)){
            editTextEmail.setError("Please enter your EmailID in correct format.");
            return;
        }

        progressDialog.setTitle("Please wait while we check your email.");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        auth.fetchSignInMethodsForEmail(emailId)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            boolean isNewUser = Objects.requireNonNull(task.getResult()).getSignInMethods().isEmpty();
                            if (isNewUser) {
                                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                                intentRegister.putExtra(emailExtraIntent, emailId);
                                startActivity(intentRegister);
                                CustomIntent.customType(MainActivity.this, "left-to-right");
                            } else {
                                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                                intentLogin.putExtra(emailExtraIntent, emailId);
                                startActivity(intentLogin);
                                CustomIntent.customType(MainActivity.this, "left-to-right");
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}
