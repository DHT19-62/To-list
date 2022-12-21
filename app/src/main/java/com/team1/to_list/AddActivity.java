package com.team1.to_list;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    Button btnAdd;
    EditText title, deadline, content;
    Calendar calendarEvent;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "Oncreate");

        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();

        addControls();

        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with a first and last name
                Map<String, Object> task = new HashMap<>();
                task.put("title", title.getText().toString());
                task.put("deadline", deadline.getText().toString());
                task.put("content", content.getText().toString());
                task.put("isComplete", false);

                // Add a new document with a generated ID
                database = FirebaseFirestore.getInstance();
                database.collection("tasks")
                        .add(task)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                finish();
            }
        });

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });
    }

    private void DatePicker() {
        int selectedYear = 2022;
        int selectedMonth = 12;
        int selectedDayOfMonth = 21;

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                deadline.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
            }
        };

        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        // Show
        datePickerDialog.show();
    }


    private void addControls() {
        btnAdd = this.<Button>findViewById(R.id.btnAdd_add);
        title = this.<EditText>findViewById(R.id.edtextTitle_add);
        deadline = this.<EditText>findViewById(R.id.edtextDeadline_add);
        content = this.<EditText>findViewById(R.id.edtextContent_add);
    }
}