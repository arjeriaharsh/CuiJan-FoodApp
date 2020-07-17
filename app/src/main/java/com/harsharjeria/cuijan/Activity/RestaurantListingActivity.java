package com.harsharjeria.cuijan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import maes.tech.intentanim.CustomIntent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.Models.Users;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestaurantListingActivity extends AppCompatActivity {

    private static String IDValue = "idOfRestaurant";
    private String idOfRestaurant;
    private RecyclerView mRecyclerQueryView2;
    private Functions functions;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference docRef;
    private DocumentReference docRefBookMark;
    private FirebaseAuth auth;

    private TextView restaurantTextView, restaurantRatingsTextView;
    private ImageView restaurantImageView1, restaurantCoverView;
    private ImageButton imageButtonCallButton, imageButtonBookmark, imageButtonDirections, imageButtonMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_listing);

        functions = new Functions();
        if(getIntent().hasExtra(IDValue)){
            idOfRestaurant = getIntent().getStringExtra(IDValue);
        }else {
            Toast.makeText(this, "Restaurant Not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
        auth = FirebaseAuth.getInstance();
        restaurantTextView = findViewById(R.id.restaurantTextView);
        restaurantRatingsTextView = findViewById(R.id.restaurantRatingsTextView);
        restaurantImageView1 = findViewById(R.id.restaurantImageView1);
        restaurantCoverView = findViewById(R.id.restaurantCoverView);
        imageButtonCallButton = findViewById(R.id.imageButtonCallButton);
        imageButtonBookmark = findViewById(R.id.imageButtonBookmark);
        imageButtonDirections = findViewById(R.id.imageButtonDirections);
        imageButtonMenu = findViewById(R.id.imageButtonMenu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        firebaseFirestore.setFirestoreSettings(settings);

        docRef = firebaseFirestore.collection("courses").document(idOfRestaurant);
        docRefBookMark = firebaseFirestore.collection("userbookmarks").document(auth.getCurrentUser().getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists()) {
                        Restaurants restaurants = document.toObject(Restaurants.class);
                        restaurantTextView.setText(restaurants.namecourse);
                        restaurantRatingsTextView.setText(restaurants.ratings + "★");
                        setTitle(restaurants.namecourse);
                        Picasso.get().load(restaurants.iconlink).into(restaurantImageView1);
                        try{
                            Picasso.get().load(restaurants.coverLink).placeholder(R.drawable.place_holder_food).into(restaurantCoverView);
                        }   catch (Exception ignored){}
                    } else {
                        Toast.makeText(RestaurantListingActivity.this, "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RestaurantListingActivity.this, "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRefBookMark.update("bm", FieldValue.arrayUnion(idOfRestaurant));
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
