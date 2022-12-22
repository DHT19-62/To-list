package com.team1.to_list;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TasksAdapter tasksAdapter;
    ArrayList<Tasks> tasks = new ArrayList<>();
    RecyclerView recyclerViewTasks;
    Button btnAdd;

    Intent intent1;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        addControls();

        addEvent();

        UpdateTasks(); // Cập nhật Task

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UpdateTasks();
    }

    private void UpdateTasks() {
        LoadDataBase();

        recyclerViewTasks = (RecyclerView)findViewById(R.id.recylerviewTasks_home);

        tasksAdapter = new TasksAdapter(tasks, this);

        Log.d(TAG, "Size of "+ String.valueOf(tasks.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerViewTasks.setAdapter(tasksAdapter);

        recyclerViewTasks.setLayoutManager(linearLayoutManager);

    }

    private void LoadDataBase() {
        database.collection("tasks")
                .orderBy("deadline")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            tasks.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String id = document.getId();
                                String title = document.get("title").toString();
                                String deadline = document.get("deadline").toString();
                                String content = document.get("content").toString();
                                boolean isComplete = document.getBoolean("isComplete");
                                tasks.add(new Tasks(id, title, content, deadline, isComplete));
                            }
                            tasksAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void addTask() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        database = FirebaseFirestore.getInstance();
        btnAdd = this.<Button>findViewById(R.id.btnAddTasks_home);
    }
}