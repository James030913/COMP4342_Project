package com.example.comp4342;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.List;

public class BookingDeleteAdapter extends ArrayAdapter<Booking> implements BookingAdapter {
    private Context context;
    private List<Booking> bookings; // Added to keep a reference to the bookings list

    public BookingDeleteAdapter(Context context, List<Booking> bookings) {
        super(context, 0, bookings);
        this.context = context;
        this.bookings = bookings; // Initialize the reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Booking booking = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_delete_booking, parent, false);
        }
        // Lookup view for data population
        TextView bookingID = convertView.findViewById(R.id.bookingID);
        TextView bookingUserID = convertView.findViewById(R.id.userID);
        TextView bookingRoomID = convertView.findViewById(R.id.roomID);
        TextView bookingCheckInDate = convertView.findViewById(R.id.checkInDate);
        TextView bookingCheckOutDate = convertView.findViewById(R.id.checkOutDate);
        TextView bookingStatus = convertView.findViewById(R.id.status);
        // Populate the data into the template view using the data object
        bookingID.setText(booking.getBookingID());
        bookingUserID.setText(booking.getUserID());
        bookingRoomID.setText(booking.getRoomID());
        bookingCheckInDate.setText(booking.getCheckInDate());
        bookingCheckOutDate.setText(booking.getCheckOutDate());
        bookingStatus.setText(booking.getBookingStatus());


        CardView deleteButton = convertView.findViewById(R.id.deleteBookingButton);
        // Inside your deleteButton.setOnClickListener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the booking and its ID
                Booking toDelete = bookings.get(position);
                String deletedBookingId = toDelete.getBookingID();

                // Construct the JSON payload
                String jsonData = "{\"ids\":[" + deletedBookingId + "]}";

                // Call the HttpTask with the DELETE method and jsonData
                new HttpTask(jsonData, "DELETE", new HttpTask.HttpDataResponse() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        bookings.remove(toDelete);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Booking ID: " + deletedBookingId + " deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        // Handle failure
                        Toast.makeText(context, "Failed to delete booking", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void addAll(List<Booking> newBookings) {
        bookings.addAll(newBookings); // Add all new bookings to the internal list
        notifyDataSetChanged(); // Notify the adapter to refresh the ListView
    }
}
