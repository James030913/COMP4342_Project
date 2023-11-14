package com.example.comp4342;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;

public class BookingEditAdapter extends ArrayAdapter<Booking> {
    private Context context;

    public BookingEditAdapter(Context context, List<Booking> bookings) {
        super(context, 0, bookings);
        this.context = context;
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
        TextView bookingUserID = convertView.findViewById(R.id.userID);
        TextView bookingRoomID = convertView.findViewById(R.id.roomID);
        TextView bookingCheckInDate = convertView.findViewById(R.id.checkInDate);
        TextView bookingCheckOutDate = convertView.findViewById(R.id.checkOutDate);
        // Populate the data into the template view using the data object
        bookingID.setText(booking.getBookingID());
        bookingUserID.setText(booking.getUserID());
        bookingRoomID.setText(booking.getRoomID());
        bookingCheckInDate.setText(booking.getCheckInDate());
        bookingCheckOutDate.setText(booking.getCheckOutDate());


        CardView editButton = convertView.findViewById(R.id.editBookingButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the Booking object for the clicked item
                Booking booking = getItem(position);

                // Create an Intent to start the new activity
                Intent intent = new Intent(context, ManagementEditBookingFragmentPage.class);

                // Put extra data into the Intent
                intent.putExtra("bookingID", booking.getBookingID());
                intent.putExtra("userID", booking.getUserID());
                intent.putExtra("roomID", booking.getRoomID());
                intent.putExtra("checkInDate", booking.getCheckInDate());
                intent.putExtra("checkOutDate", booking.getCheckOutDate());
                intent.putExtra("bookingStatus", booking.getBookingStatus());
                // ... other extras ...

                // Start the new activity
                context.startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
