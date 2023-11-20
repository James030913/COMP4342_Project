package com.example.comp4342;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FragmentManagementDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_management_dashboard);

        // Confirm Bookings CardView
        CardView confirmBookingsCard = findViewById(R.id.cardViewConfirmBookings);
        confirmBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, ManagementConfirmBookingFragment.class);
            startActivity(intent);
        });

        // Edit Bookings CardView
        CardView editBookingsCard = findViewById(R.id.cardViewEditBookings);
        editBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, ManagementEditBookingFragment.class);
            startActivity(intent);
        });

        // Delete Bookings CardView
        CardView deleteBookingsCard = findViewById(R.id.cardViewDeleteBookings);
        deleteBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, ManagementDeleteBookingFragment.class);
            startActivity(intent);
        });

        // Check-in Bookings CardView
        CardView checkInBookingsCard = findViewById(R.id.cardViewCheckInBookings);
        checkInBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, ManagementCheckinBookingFragment.class);
            startActivity(intent);
        });

        // Check-out Bookings CardView
        CardView checkOutBookingsCard = findViewById(R.id.cardViewCheckOutBookings);
        checkOutBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, ManagementCheckoutBookingFragment.class);
            startActivity(intent);
        });

        // Check-out Bookings CardView
        CardView logoutBookingsCard = findViewById(R.id.cardViewLogOut);
        logoutBookingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentManagementDashboard.this, MainActivity.class);
            startActivity(intent);
        });

//        // Log-out CardView
//        CardView logOutCard = findViewById(R.id.cardViewLogOut);
//        logOutCard.setOnClickListener(v -> {
//            // Perform log-out operations
//        });
    }
}
