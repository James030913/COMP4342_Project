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

public class BookingCheckoutAdapter extends ArrayAdapter<Booking> {
    private Context context;
    private List<Booking> bookings; // Added to keep a reference to the bookings list

    public BookingCheckoutAdapter(Context context, List<Booking> bookings) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_checkout_booking, parent, false);
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


        CardView checkoutButton = convertView.findViewById(R.id.checkoutBookingButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the booking and its ID
                Booking toCheckout = bookings.get(position);
                String checkoutedBookingId = toCheckout.getBookingID();

                bookings.remove(toCheckout);

                // Notify the adapter about the dataset change
                notifyDataSetChanged();

                // Show a Toast message including the booking ID
                Toast.makeText(context, "Booking ID : " + checkoutedBookingId + " Checked-out", Toast.LENGTH_SHORT).show();

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
