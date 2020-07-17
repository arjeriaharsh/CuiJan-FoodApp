package com.harsharjeria.cuijan.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_search extends Fragment {


    private EditText searchEditText;
    private RecyclerView mRecyclerQueryView;
    private FirestoreRecyclerAdapter<Restaurants, ProductViewHolder> adapter;


    private Functions functions;
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        functions = new Functions();

        searchEditText = v.findViewById(R.id.searchViewMain);
        toolbar = v.findViewById(R.id.toolbar);
        mRecyclerQueryView = v.findViewById(R.id.recyclerViewQuery2);
        mRecyclerQueryView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (functions.isEmpty(searchEditText)){
                        searchEditText.setError("Please enter something to search.");
                        return false;
                    }
                    launch(searchEditText.getText().toString());
                }
                return false;
            }
        });
        return v;
    }


    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textServiceName;
        private TextView textServicePrice;
        private TextView textServiceType;
        private ImageView imageView;

        ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textServiceName = view.findViewById(R.id.name);
            textServicePrice = view.findViewById(R.id.website);
            textServiceType = view.findViewById(R.id.textView3);
            imageView = view.findViewById(R.id.logoIcon);
        }

        void setProductName(String productName) {
            textServiceName.setText(productName);
        }
    }

    private void launch(String searchText){
        if(adapter != null){
            adapter.stopListening();
        }

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        final CollectionReference citiesRef = rootRef.collection("courses");
        Query query = citiesRef.orderBy("namecourse").startAt(searchText).endAt(searchText + "\uf8ff");

        FirestoreRecyclerOptions<Restaurants> options = new FirestoreRecyclerOptions.Builder<Restaurants>()
                .setQuery(query, Restaurants.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Restaurants, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Restaurants model) {
                holder.setProductName(model.getNamecourse());
                holder.textServicePrice.setText(functions.myCuisine()[model.website].websiteName + " - "+ model.getRatings() + "★");
                holder.textServiceType.setText(model.getPriceAmount() + "");

                Picasso.get().load(functions.myCuisine()[model.website].websiteIconLink).into(holder.imageView);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_courses, parent, false);
                return new ProductViewHolder(view);
            }
        };
        mRecyclerQueryView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
