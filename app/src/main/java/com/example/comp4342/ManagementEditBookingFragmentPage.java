package com.example.comp4342;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ManagementEditBookingFragmentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_booking_onclickeditbtn);

        // Retrieve data from Intent
        Intent intent = getIntent();
        String bookingID = intent.getStringExtra("bookingID");
        String userID = intent.getStringExtra("userID");
        String roomID = intent.getStringExtra("roomID");
        String checkInDate = intent.getStringExtra("checkInDate");
        String checkOutDate = intent.getStringExtra("checkOutDate");
        String bookingStatus = intent.getStringExtra("bookingStatus");

        // Get references to EditText views
        EditText editBookingID = findViewById(R.id.editBookingID);
        EditText editUserID = findViewById(R.id.editUserID);
        EditText editRoomID = findViewById(R.id.editRoomID);
        EditText editCheckInDate = findViewById(R.id.editCheckInDate);
        EditText editCheckOutDate = findViewById(R.id.editCheckOutDate);

        // Set the text for each EditText
        editBookingID.setText(bookingID);
        editUserID.setText(userID);
        editRoomID.setText(roomID);
        editCheckInDate.setText(checkInDate);
        editCheckOutDate.setText(checkOutDate);



        // ... set other booking details as needed
    }
}
