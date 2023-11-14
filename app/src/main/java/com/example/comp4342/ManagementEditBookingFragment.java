package com.example.comp4342;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManagementEditBookingFragment extends AppCompatActivity {

    private ListView listView;
    private BookingEditAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard_confirm_bookings);

        listView = findViewById(R.id.listView);
        adapter = new BookingEditAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);


        // Pass the adapter to FetchJsonTask
        new FetchJsonTask(adapter).execute("https://great-grown-opossum.ngrok-free.app/bookings");
    }


}
