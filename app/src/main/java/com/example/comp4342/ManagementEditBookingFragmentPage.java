package com.example.comp4342;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManagementEditBookingFragmentPage extends AppCompatActivity {
    private Spinner spinnerStatus;

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
        spinnerStatus = findViewById(R.id.spinnerStatus);

        // Set the text for each EditText
        editBookingID.setText(bookingID);
        editUserID.setText(userID);
        editRoomID.setText(roomID);
        editCheckInDate.setText(checkInDate);
        editCheckOutDate.setText(checkOutDate);
//        editStatus.setText(bookingStatus);

        spinnerStatus = findViewById(R.id.spinnerStatus);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.booking_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerStatus.setAdapter(adapter);

        // If you have a status from the Intent, select it in the Spinner
        if (bookingStatus != null) {
            int spinnerPosition = adapter.getPosition(bookingStatus);
            spinnerStatus.setSelection(spinnerPosition);
        }

        // ... set other booking details as needed
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated data from EditText fields
                String updatedBookingID = editBookingID.getText().toString();
                String updatedUserID = editUserID.getText().toString();
                String updatedRoomID = editRoomID.getText().toString();
                String updatedCheckInDate = editCheckInDate.getText().toString();
                String updatedCheckOutDate = editCheckOutDate.getText().toString();
                String updatedStatus = spinnerStatus.getSelectedItem().toString();

                // Construct the JSON payload
                String jsonData = "[" +
                        "{" +
                        "\"bookingID\": " + updatedBookingID + "," +
                        "\"userID\": " + updatedUserID + "," +
                        "\"roomID\": " + updatedRoomID + "," +
                        "\"checkInDate\": \"" + updatedCheckInDate + "\"," +
                        "\"checkOutDate\": \"" + updatedCheckOutDate + "\"," +
                        "\"bookingStatus\": \"" + updatedStatus + "\"" +
                        // Add other details as necessary
                        "}" +
                        "]";

                // Use HttpTask to send the PUT request
                new HttpTask(jsonData, "PUT", new HttpTask.HttpDataResponse() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        Toast.makeText(ManagementEditBookingFragmentPage.this, "Update successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        // Handle failure
                        Toast.makeText(ManagementEditBookingFragmentPage.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }
        });
    }
}

