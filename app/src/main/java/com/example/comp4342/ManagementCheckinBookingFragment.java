package com.example.comp4342;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManagementCheckinBookingFragment extends AppCompatActivity {

    private ListView listView;
    private BookingCheckinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard_checkin_bookings);

        listView = findViewById(R.id.listView);
        adapter = new BookingCheckinAdapter(this, new ArrayList<>());
        listView.setAdapter((ListAdapter) adapter);

        listView = findViewById(R.id.listView);
        adapter = new BookingCheckinAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        adapter.add(new Booking("408", "4", "121", "2023-10-11", "2023-11-26", "Confirmed"));
        adapter.add(new Booking("410", "1", "192", "2023-10-04", "2023-10-22", "Confirmed"));
        adapter.add(new Booking("411", "7", "53", "2023-10-27", "2023-11-09", "Confirmed"));


//        Toast.makeText(ManagementEditBookingFragment.this, "This is a Toast message!", Toast.LENGTH_SHORT).show();

        // Pass the adapter to FetchJsonTask
//        new FetchJsonTask(adapter).execute("https://great-grown-opossum.ngrok-free.app/bookings");



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
