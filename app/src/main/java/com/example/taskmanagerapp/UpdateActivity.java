package com.example.taskmanagerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    ImageButton backButton;
    Button updateButton, deleteButton;
    EditText title_input, desc_input, date_input;
    String id, title, desc, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, Homepage.class);
                startActivity(intent);
            }
        });

        title_input = findViewById(R.id.updateTaskTitle);
        desc_input = findViewById(R.id.updateTaskDescription);
        date_input = findViewById(R.id.updateDueDate);
        date_input.setInputType(InputType.TYPE_NULL);
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_input.clearFocus();
                showDatePicker();
            }
        });
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        getSetIntentDate();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_input.getText().toString();
                desc = desc_input.getText().toString();
                date = date_input.getText().toString();
                MyDatebase myDB = new MyDatebase(UpdateActivity.this);
                myDB.updateData(id, title, desc, date);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getSetIntentDate(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title")
        && getIntent().hasExtra("desc") && getIntent().hasExtra("date")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            desc = getIntent().getStringExtra("desc");
            date = getIntent().getStringExtra("date");

            title_input.setText(title);
            desc_input.setText(desc);
            date_input.setText(date);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        date_input.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete this task ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatebase myDB = new MyDatebase(UpdateActivity.this);
                myDB.deleteRow(id);
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}