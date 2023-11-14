package com.example.comp4342;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EditBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard_edit_bookings);

        // Example: Find your views and set their values
        TextView bookingID = findViewById(R.id.bookingID);
        TextView userID = findViewById(R.id.userID);
        // ...other TextViews

        // Example: Set values
        bookingID.setText("12345");
        userID.setText("User1");
        // ...set other TextView values

        // Example: Handle CardView click events
        CardView confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(view -> {
            // Handle confirm click
        });

        // Additional logic to populate data into other views
    }
}
