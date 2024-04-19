package com.example.taskmanagerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;


public class Homepage extends AppCompatActivity {
    TextView title;
    RecyclerView taskLists;
    FloatingActionButton addTask;
    MyDatebase myDB;
    ArrayList<String> task_id, task_title, task_desc, task_date;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        taskLists = findViewById(R.id.task_lists);
        addTask = findViewById(R.id.add_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, AddTask.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatebase(Homepage.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        task_desc = new ArrayList<>();
        task_date = new ArrayList<>();

        storeAndSortData();

        customAdapter = new CustomAdapter(this, task_id, task_title, task_desc, task_date, Homepage.this);
        taskLists.setAdapter(customAdapter);
        taskLists.setLayoutManager(new LinearLayoutManager(Homepage.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            storeAndSortData();
            recreate();
        }
    }

    void storeAndSortData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(Homepage.this, "No data!", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<Task> tasks = new ArrayList<>();
            while (cursor.moveToNext()) {
                Task task = new Task(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                tasks.add(task);
            }

            // Sort tasks by due date
            tasks.sort(Comparator.comparing(Task::getDate));

            // Clear existing data
            task_id.clear();
            task_title.clear();
            task_desc.clear();
            task_date.clear();

            // Populate sorted data into ArrayLists
            for (Task task : tasks) {
                task_id.add(task.getId());
                task_title.add(task.getTitle());
                task_desc.add(task.getDescription());
                task_date.add(task.getDate());
            }
        }
    }

    public class Task {
        private String id;
        private String title;
        private String description;
        private String date;

        public Task(String id, String title, String description, String date) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }
    }

}