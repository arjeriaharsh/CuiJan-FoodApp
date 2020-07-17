package com.harsharjeria.cuijan.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.harsharjeria.cuijan.Activity.RestaurantListingActivity;
import com.harsharjeria.cuijan.Adapters.ClassesHomeList;
import com.harsharjeria.cuijan.Adapters.WebsiteHomeList;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.Models.Users;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import maes.tech.intentanim.CustomIntent;

public class fragment_home extends Fragment {

    private RecyclerView myRecyclerViewWebsites, myRecyclerViewWebsites2, yRecyclerViewHot;
    private Functions functions;
    private DocumentReference docRef;
    private CollectionReference docRef2;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;

    private TextView nameTextView;
    private TextView test;

    private FirestoreRecyclerAdapter<Restaurants, fragment_home.ProductViewHolder> adapter;
    private static String IDValue = "idOfRestaurant";

    private ShimmerFrameLayout include1, include2, include3;
    int test1 = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        functions = new Functions();
        auth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);

        docRef = firebaseFirestore.collection("users").document(Objects.requireNonNull(auth.getUid()));

        docRef2 = firebaseFirestore.collection("courses");

        myRecyclerViewWebsites = v.findViewById(R.id.recyclerviewWebsites);
        yRecyclerViewHot = v.findViewById(R.id.recyclerviewTop);
        test = v.findViewById(R.id.imageView3);
        include1 = v.findViewById(R.id.include1);
        include2 = v.findViewById(R.id.include2);
        include3 = v.findViewById(R.id.include3);

        myRecyclerViewWebsites2 = v.findViewById(R.id.recyclerviewClasses);
        nameTextView = v.findViewById(R.id.textWelcomeName);
        nameTextView.setText("Welcome!\n");

        WebsiteHomeList websiteHomeList = new WebsiteHomeList(getContext(), functions.myCuisine());
        ClassesHomeList classesHomeList = new ClassesHomeList(getContext(), functions.myFoodType());

        myRecyclerViewWebsites.setHasFixedSize(true);
        myRecyclerViewWebsites.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
        myRecyclerViewWebsites.setAdapter(websiteHomeList);

        yRecyclerViewHot.setHasFixedSize(true);
        yRecyclerViewHot.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        myRecyclerViewWebsites2.setHasFixedSize(true);
        myRecyclerViewWebsites2.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
        myRecyclerViewWebsites2.setAdapter(classesHomeList);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists()) {
                        Users users = document.toObject(Users.class);
                        nameTextView.append(Objects.requireNonNull(users).getName());
                        if(users.permLevel == 1){
                            test.setText("Admin");
                            test.setTextColor(Color.RED);
                        }
                    } else {
                        Toast.makeText(getContext(), "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Contact Us, Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        launch();

        return v;
    }

    private void switchShimmer(){
        if (include1.getVisibility() == View.VISIBLE){
            include1.setVisibility(View.INVISIBLE);
            include2.setVisibility(View.INVISIBLE);
            include3.setVisibility(View.INVISIBLE);
            include1.stopShimmer();
            include2.stopShimmer();
            include3.stopShimmer();
        }else {
            include1.setVisibility(View.VISIBLE);
            include2.setVisibility(View.VISIBLE);
            include3.setVisibility(View.VISIBLE);
            include1.startShimmer();
            include2.startShimmer();
            include3.startShimmer();
        }

    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textServiceName, textStar;
        private ImageView imageView;

        ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textServiceName = view.findViewById(R.id.textHot);
            textStar = view.findViewById(R.id.textStar);
            imageView = view.findViewById(R.id.iconId);
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
        final Query query = citiesRef.orderBy("ratings", Query.Direction.DESCENDING).limit(10);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                    if (test1 == 1){
                        switchShimmer();
                        test1++;
                    }
                }else if (queryDocumentSnapshots != null && queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(getContext(), "This service block is empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Error, please contact on email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FirestoreRecyclerOptions<Restaurants> options = new FirestoreRecyclerOptions.Builder<Restaurants>()
                .setQuery(query, Restaurants.class)

                .build();

        adapter = new FirestoreRecyclerAdapter<Restaurants, fragment_home.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull fragment_home.ProductViewHolder holder, int position, @NonNull final Restaurants model) {
                holder.setProductName(model.getNamecourse());
                holder.textStar.setText(model.ratings + "★");
                Picasso.get().load(model.iconlink).into(holder.imageView);

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openActivityRestaurant = new Intent(getContext(), RestaurantListingActivity.class);
                        openActivityRestaurant.putExtra(IDValue, model.getIdcourse());
                        Objects.requireNonNull(getActivity()).startActivity(openActivityRestaurant);
                        CustomIntent.customType(getContext(), "left-to-right");
                    }
                });
            }

            @NonNull
            @Override
            public fragment_home.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_topics, parent, false);
                return new fragment_home.ProductViewHolder(view);
            }
        };
        yRecyclerViewHot.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        if (adapter != null) {
            adapter.stopListening();
            switchShimmer();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (adapter != null) {
            adapter.stopListening();
            switchShimmer();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adapter != null) {
            adapter.stopListening();
            switchShimmer();
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
