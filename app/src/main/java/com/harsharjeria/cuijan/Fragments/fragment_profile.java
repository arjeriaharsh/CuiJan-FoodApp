package com.harsharjeria.cuijan.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsharjeria.cuijan.Activity.AddActivity;
import com.harsharjeria.cuijan.Activity.MainActivity;
import com.harsharjeria.cuijan.Activity.UserProfileActivity;
import com.harsharjeria.cuijan.Models.Users;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import maes.tech.intentanim.CustomIntent;

public class fragment_profile extends Fragment {

    private AppCompatButton signOutButton, adminButton, editProfileButton;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private Functions functions;
    private DocumentReference docRef;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        functions = new Functions();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        docRef = firebaseFirestore.collection("users").document(Objects.requireNonNull(auth.getUid()));

        signOutButton = v.findViewById(R.id.signOutButton);
        adminButton = v.findViewById(R.id.adminButton);
        editProfileButton = v.findViewById(R.id.editProfileButton);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent goback = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(goback);
                getActivity().finishAffinity();
            }
        });
        adminButton.setVisibility(View.GONE);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(getActivity(), AddActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(admin);
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(getActivity(), UserProfileActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(admin);
                CustomIntent.customType(getContext(), "left-to-right");
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists()) {
                        Users users = document.toObject(Users.class);
                        if(users.permLevel == 1){
                            adminButton.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }
                } else {

                }
            }
        });

        return v;
    }



}
