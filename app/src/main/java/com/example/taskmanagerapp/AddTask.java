package com.example.taskmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;


public class AddTask extends AppCompatActivity {

    ImageButton backButton;
    EditText title, desc, date;
    Button addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this, Homepage.class);
                startActivity(intent);
            }
        });

        title = findViewById(R.id.editTaskTitle);
        desc = findViewById(R.id.editTaskDescription);
        date = findViewById(R.id.editDueDate);

        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.clearFocus();
                showDatePicker();
            }
        });

        addTask = findViewById(R.id.add_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatebase myDB = new MyDatebase(AddTask.this);
                myDB.addTask(title.getText().toString().trim(), desc.getText().toString().trim(),
                        date.getText().toString());
            }
        });
    }
    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddTask.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        date.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }
}