package com.harsharjeria.cuijan.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsharjeria.cuijan.Models.Restaurants;
import com.harsharjeria.cuijan.R;
import com.harsharjeria.cuijan.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText addLink, addCourseSubject, addDescription, addPrice, addCourseName;
    private Spinner websiteType;
    private Button addServiceButton;
    private FirebaseFirestore rootRef;
    private CollectionReference citiesRef;
    private int selectedItem;

    private Functions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Admin Panel");
        functions = new Functions();

        rootRef = FirebaseFirestore.getInstance();
        citiesRef = rootRef.collection("courses");


        addLink = findViewById(R.id.addLink);
        addCourseSubject = findViewById(R.id.addCourseSubject);
        addDescription = findViewById(R.id.addDescription);
        addPrice = findViewById(R.id.addPrice);
        addCourseName = findViewById(R.id.addCourseName);
        websiteType = findViewById(R.id.websiteType);
        addServiceButton = findViewById(R.id.addServiceButton);


        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Chinese");
        spinnerArray.add("Japanese");
        spinnerArray.add("North Indian");
        spinnerArray.add("South Indian");
        spinnerArray.add("Gujarati");
        spinnerArray.add("City Special");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddActivity.this,
                R.layout.spinner_item,
                spinnerArray
        );
        websiteType.setAdapter(adapter);

        websiteType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                selectedItem = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                selectedItem = 0;
            }

        });

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()){
                    final ProgressDialog newProgress = new ProgressDialog(AddActivity.this);
                    newProgress.setTitle("Loading...");
                    newProgress.show();
                    String idBefore = rootRef.collection("services").document().getId();
//                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Restaurants serviceModel = new Restaurants(
                            addCourseName.getText().toString().trim(),
                            idBefore,
                            addLink.getText().toString().trim(),
                            String.valueOf(selectedItem),
                            selectedItem,
                            0.0,
                            0.0,
                            addPrice.getText().toString().trim(),
                            addDescription.getText().toString().trim(),
                            "gwl",
                            "0"
                    );

                    citiesRef.document(idBefore).set(serviceModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            newProgress.dismiss();
                            Toast.makeText(AddActivity.this, "Service added successfully.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            newProgress.dismiss();
                            Toast.makeText(AddActivity.this, "Problem adding this service, please check your internet connection.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });



    }

    private boolean isEmpty() {
        if (addCourseName.getText().toString().trim().length() == 0){
            addCourseName.setError("Please enter a name.");
            return false;
        }

        if (addCourseSubject.getText().toString().trim().length() == 0){
            addCourseSubject.setError("Please enter timings.");
            return false;
        }

        if (addDescription.getText().toString().trim().length() == 0 && addDescription.getText().toString().length() > 800){
            addDescription.setError("Please enter a description or longer than 800 characters.");
            return false;
        }

        if (addPrice.getText().toString().trim().length() == 0){
            addPrice.setError("Please enter a price/free.");
            return false;
        }
        return true;
    }
}
