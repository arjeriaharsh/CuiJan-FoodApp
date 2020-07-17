package com.harsharjeria.cuijan.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import maes.tech.intentanim.CustomIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerQueryView2;
    private FirestoreRecyclerAdapter<Restaurants, ProductViewHolder> adapter;
    private Functions functions;
    private ProgressBar progress_circular;
    private ImageView imageForCuisine;
    private int websiteID;
    private static String IDValue = "idOfRestaurant";

    private static String intValue = "websiteId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        functions = new Functions();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerQueryView2 = findViewById(R.id.recyclerViewQuery2);
        imageForCuisine = findViewById(R.id.imageForCuisine);

        mRecyclerQueryView2.setLayoutManager(new LinearLayoutManager(this));
        if(getIntent().hasExtra(intValue)){
            websiteID = getIntent().getIntExtra(intValue, 0);
        }
        progress_circular = findViewById(R.id.progress_circular);

        setTitle(functions.myCuisine()[websiteID].websiteName);

        launch();

        Picasso.get().load(functions.myCuisine()[websiteID].websiteIconLink).into(imageForCuisine);

    }


    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textServiceName;
        private TextView textServicePrice;
        private TextView textServiceType;
        private TextView rating;
        private ImageView imageView;

        ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textServiceName = view.findViewById(R.id.name);
            textServicePrice = view.findViewById(R.id.website);
            textServiceType = view.findViewById(R.id.textView3);
            rating = view.findViewById(R.id.rating);
            imageView = view.findViewById(R.id.logoIcon);
        }

        void setProductName(String productName) {
            textServiceName.setText(productName);
        }
    }

    private void launch(){
        if(adapter != null){
            adapter.stopListening();
        }

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        final CollectionReference citiesRef = rootRef.collection("courses");
        Query query = citiesRef.whereEqualTo("website", websiteID).orderBy("namecourse");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                    progress_circular.setVisibility(View.INVISIBLE);
                }else if (queryDocumentSnapshots != null && queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(SearchResultActivity.this, "This service block is empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SearchResultActivity.this, "Error, please contact on email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FirestoreRecyclerOptions<Restaurants> options = new FirestoreRecyclerOptions.Builder<Restaurants>()
                .setQuery(query, Restaurants.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Restaurants, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Restaurants model) {
                holder.setProductName(model.getNamecourse());
                holder.textServicePrice.setText(functions.myCuisine()[model.website].websiteName);
                holder.textServiceType.setText(model.getPriceAmount() + "");
                holder.rating.setText(model.getRatings() + "★");
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openActivityRestaurant = new Intent(SearchResultActivity.this, RestaurantListingActivity.class);
                        openActivityRestaurant.putExtra(IDValue, model.getIdcourse());
                        startActivity(openActivityRestaurant);
                        CustomIntent.customType(SearchResultActivity.this, "left-to-right");
                    }
                });
                Picasso.get().load(model.iconlink).into(holder.imageView);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_courses, parent, false);
                return new ProductViewHolder(view);
            }
        };
        mRecyclerQueryView2.setAdapter(adapter);
        adapter.startListening();
        adapter.stopListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPause() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adapter != null) {
            adapter.stopListening();
        }
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
