package com.example.comp4342;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookingEditAdapter extends ArrayAdapter<Booking> {
    public BookingEditAdapter(Context context, List<Booking> bookings) {
        super(context, 0, bookings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Booking booking = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_edit_booking, parent, false);
        }
        // Lookup view for data population
        TextView bookingID = convertView.findViewById(R.id.bookingID);
        // Populate the data into the template view using the data object
        bookingID.setText(booking.getBookingID());
        // Return the completed view to render on screen
        return convertView;
    }
}
