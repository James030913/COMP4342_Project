package com.example.comp4342;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessActivity extends AppCompatActivity {

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        back = findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here
                // For example, finish the current activity to "go back"
                Intent intent = new Intent(view.getContext(), HomeActivity.class);

                // If you want to clear all the activities on top of HomeActivity in the stack,
                // you can add the following flags to the Intent:
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // Start HomeActivity
                startActivity(intent);
            }
        });
    }
}