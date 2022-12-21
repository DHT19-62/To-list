package com.team1.to_list;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    EditText title, deadline, content;
    Button btnEdit, btnSave, btnDelete;
    CheckBox checkBox;
    Intent intent;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        intent = getIntent();

        addControls();

        addEvents();
    }

    private void addEvents() {

        btnEdit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setEnabled(true);
                deadline.setEnabled(true);
                content.setEnabled(true);
                checkBox.setEnabled(true);
                btnSave.setVisibility(View.VISIBLE);
            }
        }));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Creatnewtask();
                Deletecurrenttask();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletecurrenttask();
                finish();
            }
        });
    }

    private void Deletecurrenttask() {
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("tasks")
                .document(intent.getStringExtra("id"));

        ref.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Delete successful id: "+intent.getStringExtra("id"));
                }else {
                    Log.d(TAG, "Delete failed");
                }
            }
        });
    }

    private void Creatnewtask() {
        Map<String, Object> task = new HashMap<>();
        task.put("title", title.getText().toString());
        task.put("deadline", deadline.getText().toString());
        task.put("content", content.getText().toString());
        task.put("isComplete", checkBox.isChecked());

        // Add a new document with a generated ID
        db = FirebaseFirestore.getInstance();
        db.collection("tasks")
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addControls() {

        title = this.<EditText>findViewById(R.id.edtextTitle_detail);
        title.setText(intent.getStringExtra("title"));

        deadline = this.<EditText>findViewById(R.id.edtextDeadline_detail);
        deadline.setText(intent.getStringExtra("deadline"));

        content = this.<EditText>findViewById(R.id.edtextContent_detail);
        content.setText(intent.getStringExtra("content"));

        checkBox = this.<CheckBox>findViewById(R.id.checkBox_detail);
        checkBox.setChecked(intent.getBooleanExtra("isComplete", true));

        btnSave = this.<Button>findViewById(R.id.btnSave_detail);
        btnEdit = this.<Button>findViewById(R.id.btnEdit_detail);

        btnDelete = this.<Button>findViewById(R.id.btnDelete_detail);
    }
}