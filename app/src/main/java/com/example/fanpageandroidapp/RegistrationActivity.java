package com.example.fanpageandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registerSubmitBtn = (Button) findViewById(R.id.regSubmitBtn);
        TextView userEmail = (TextView) findViewById(R.id.userEmailTv);
        TextView firstNameTv = (TextView) findViewById(R.id.firstNameTv);
        TextView lastNameTv = (TextView) findViewById(R.id.lastNameTv);

        //get registration datetime
        String currentDate= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        registerSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String firstName = firstNameTv.getText().toString();
                String lastName = lastNameTv.getText().toString();
                Log.d("date", currentDate);
                String unqiueID = db.collection("users").document().getId();

                // Create a new user
                Map<String, Object> user = new HashMap<>();
                user.put("email", email);
                user.put("id", unqiueID);
                user.put("first", firstName);
                user.put("last", lastName);
                user.put("role", "customer");
                user.put("dateTime", currentDate);

                // Add a new user to users collection
                db.collection("users").document(email)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(startIntent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });

            }
        });



    }
}