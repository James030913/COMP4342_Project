package com.example.comp4342;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ManagementEditBookingFragment extends AppCompatActivity {

    private ListView listView;
    private BookingEditAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard_edit_bookings);

        listView = findViewById(R.id.listView);
        adapter = new BookingEditAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);


        // Set an OnItemClickListener on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the Booking object for the clicked row
                Booking booking = (Booking) adapter.getItem(position);

                // Create an Intent to start the new activity
                Intent intent = new Intent(ManagementEditBookingFragment.this, ManagementEditBookingFragmentPage.class);

                // Put extra data into the Intent
//                assert booking != null;
//                Toast.makeText(ManagementEditBookingFragment.this, booking.getCheckOutDate(), Toast.LENGTH_SHORT).show();
                intent.putExtra("bookingID", booking.getBookingID());
                intent.putExtra("userID", booking.getUserID());
                intent.putExtra("roomID", booking.getRoomID());
                intent.putExtra("checkInDate", booking.getCheckInDate());
                intent.putExtra("checkOutDate", booking.getCheckOutDate());
                intent.putExtra("bookingStatus", booking.getBookingStatus());
//                // ... put other booking details as needed
//
//                // Start the new activity
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listView);
        adapter = new BookingEditAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

//        adapter.add(new Booking("408", "4", "121", "2023-10-11", "2023-11-26", "Confirmed"));
//        adapter.add(new Booking("410", "1", "192", "2023-10-04", "2023-10-22", "Confirmed"));
//        adapter.add(new Booking("411", "7", "53", "2023-10-27", "2023-11-09", "Confirmed"));


//        Toast.makeText(ManagementEditBookingFragment.this, "This is a Toast message!", Toast.LENGTH_SHORT).show();

        // Pass the adapter to FetchJsonTask
        new FetchJsonTask(adapter).execute("https://great-grown-opossum.ngrok-free.app/bookings");



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear the adapter data
        if (adapter != null) {
            adapter.clear();
        }
    }


}
