package com.harsharjeria.cuijan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import maes.tech.intentanim.CustomIntent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.Models.Users;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    private Functions functions;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference docRef;
    private FirebaseAuth auth;
    private String fireAuthUID;

    private TextView userTextView, userRatingsTextView;
    private EditText editTextName, editTextFavoriteFood;
    private ImageButton editItemButton, editImageButton;
    private Button changePasswordButton, saveSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        functions = new Functions();
        auth = FirebaseAuth.getInstance();
        try {
            fireAuthUID = auth.getCurrentUser().getUid();
        }catch (Exception ignore){}

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userTextView = findViewById(R.id.userTextView);
        userRatingsTextView = findViewById(R.id.userRatingsTextView);
        editTextName = findViewById(R.id.editTextName);
        editTextFavoriteFood = findViewById(R.id.editTextFavoriteFood);
        editItemButton = findViewById(R.id.editItemButton);
        editImageButton = findViewById(R.id.editImageButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setVisibility(View.INVISIBLE);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        firebaseFirestore.setFirestoreSettings(settings);

        docRef = firebaseFirestore.collection("users").document(fireAuthUID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists()) {
                        Users users = document.toObject(Users.class);
                        userTextView.setText(users.name);
                        userRatingsTextView.setText(users.favfood);
                        editTextName.setText(users.name);
                        editTextFavoriteFood.setText(users.favfood);
                    } else {
                        Toast.makeText(UserProfileActivity.this, "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserProfileActivity.this, "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfileActivity.this, "//TODO", Toast.LENGTH_SHORT).show();
            }
        });
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(false);
                editTextFavoriteFood.setEnabled(false);
                editItemButton.setVisibility(View.VISIBLE);
                saveSettingsButton.setVisibility(View.INVISIBLE);
            }
        });
        editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemButton.setVisibility(View.INVISIBLE);
                saveSettingsButton.setVisibility(View.VISIBLE);
                editTextName.setEnabled(true);
                editTextFavoriteFood.setEnabled(true);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
