package com.example.fanpageandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button adminBtn = (Button)findViewById(R.id.adminBtn);
        TextView messageBox = (TextView) findViewById(R.id.userActTv);
        Button postBtn = (Button) findViewById(R.id.userActPostbtn);
        Button closeBtn = (Button) findViewById(R.id.userActCloseBtn);
        Button viewMessageBtn = (Button) findViewById(R.id.userViewMessBtn);


        if (getIntent().hasExtra("userEmail")){
            String userEmail = getIntent().getExtras().getString("userEmail");
            //check to see if user is an admin

            DocumentReference docIdRef = db.collection("users").document(userEmail);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String role = document.getString("role");
                            if(role.equals("admin")) {
                                adminBtn.setVisibility(View.VISIBLE);

                            }else{
                                Log.d("YAY", "role");
                                Intent startIntent = new Intent(getApplicationContext(), CustomerActivity.class);
                                startActivity(startIntent);
                            }

                        }
                    }
                }
            });

        }

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageBox.setVisibility(View.VISIBLE);
                postBtn.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.VISIBLE);
            }
        });
        // add data to db

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageBox.getText().toString();
                String currentDate= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Map<String, Object> userMessage = new HashMap<>();
                userMessage.put("content", message);
                userMessage.put("dateTime", currentDate);

                db.collection("messages")
                        .add(userMessage)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageBox.setVisibility(View.GONE);
                postBtn.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
            }
        });

        viewMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CustomerActivity.class);
                startActivity(startIntent);
            }
        });



    }
}