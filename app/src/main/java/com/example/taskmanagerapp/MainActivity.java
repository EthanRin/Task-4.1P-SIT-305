package com.example.taskmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.inputName);

        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                if (userName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Username can't be empty!", Toast.LENGTH_SHORT);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    startActivity(intent);
                }
            }
        });
    }
}