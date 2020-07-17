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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsharjeria.cuijan.Models.Users;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextView textView2, textView;
    private String emailID;
    private static String emailExtraIntent = "emailExtraIntent";

    private TextInputEditText editTextName, editTextPassword, editTextPasswordConfirm, editTextQuaLification;

    private Functions functions;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        functions = new Functions();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //FAB
        floatingActionButton = findViewById(R.id.floatingActionButton);

        //TextViews
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        //Email iD
        emailID = getIntent().getStringExtra(emailExtraIntent);

        //EditTexts
        editTextName = findViewById(R.id.nameEditTextReal);
        editTextPassword = findViewById(R.id.passwordEditTextReal);
        editTextPasswordConfirm = findViewById(R.id.cpasswordEditTextReal);
        editTextQuaLification = findViewById(R.id.qualificationEditTextReal);

        //Functions
        textView2.setText("Registering as\n" + emailID);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });



    }

    private boolean validateData(){
        if (functions.isEmpty(editTextName)){
            editTextName.setError("Please enter a name.");
            return false;
        }
        if (functions.isEmpty(editTextPassword) || Objects.requireNonNull(editTextPassword.getText()).length() < 7){
            editTextPassword.setError("Please enter a six digit password.");
            return false;
        }
        if (functions.isEmpty(editTextPasswordConfirm)){
            editTextPasswordConfirm.setError("Please confirm your password.");
            return false;
        }
        if(!Objects.requireNonNull(editTextPasswordConfirm.getText()).toString()
                .equals(Objects.requireNonNull(editTextPassword.getText()).toString())){
            editTextPasswordConfirm.setError("Please confirm your password, it doesn't match.");
            return false;
        }
        if (functions.isEmpty(editTextQuaLification)){
            editTextQuaLification.setError("Please enter a qualification.");
            return false;
        }
        return true;
    }

    private void registerNewUser(){
        if(validateData()){
            final String email = emailID;
            String password = Objects.requireNonNull(editTextPassword.getText()).toString();
            final String name = Objects.requireNonNull(editTextName.getText()).toString();
            final String qualification = Objects.requireNonNull(editTextQuaLification.getText()).toString();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(false);
                            progressDialog.setTitle("Registering!");
                            progressDialog.show();
                            if (task.isSuccessful()) {
                                String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                Users users = new Users(uid, name, emailID, qualification, 0);
                                firebaseFirestore.collection("users").document(uid).set(users)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Intent startApp = new Intent(RegisterActivity.this, HomePage.class);
                                        startActivity(startApp);
                                        CustomIntent.customType(RegisterActivity.this, "left-to-right");
                                        finishAffinity();
                                    }})
                                        .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Database failure, contact us for fixing.",
                                                Toast.LENGTH_SHORT).show();
                                    }});
                            } else {
                                Toast.makeText(RegisterActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
