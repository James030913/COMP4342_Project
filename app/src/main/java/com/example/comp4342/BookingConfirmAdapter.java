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

public class BookingConfirmAdapter extends ArrayAdapter<Booking> implements BookingAdapter {
    private Context context;
    private List<Booking> bookings; // Added to keep a reference to the bookings list

    public BookingConfirmAdapter(Context context, List<Booking> bookings) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_confirm_booking, parent, false);
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

        CardView confirmButton = convertView.findViewById(R.id.confirmBookingButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking toConfirm = bookings.get(position);



                // Construct the JSON data
                String jsonData = "[" +
                        "{" +
                        "\"bookingID\": " + toConfirm.getBookingID() + "," +
                        "\"userID\": " + toConfirm.getUserID() + "," +
                        "\"roomID\": " + toConfirm.getRoomID() + "," +
                        "\"checkInDate\": \"" + toConfirm.getCheckInDate() + "\"," +
                        "\"checkOutDate\": \"" + toConfirm.getCheckOutDate() + "\"," +
                        "\"bookingStatus\": \"Confirm\"" +
                        "}" +
                        "]";

//                Toast.makeText(context, jsonData, Toast.LENGTH_SHORT).show();


                new HttpTask(jsonData, "PUT", new HttpTask.HttpDataResponse() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response
                        Toast.makeText(context, "Booking confirmed successfully", Toast.LENGTH_SHORT).show();
                        // Update the list and notify the adapter
                        bookings.remove(toConfirm);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        // Handle the failure
                        Toast.makeText(context, "Failed to confirm booking", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }
        });


        // Setup for Reject Button
        CardView rejectButton = convertView.findViewById(R.id.rejectBookingButton);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the booking and its ID
                Booking toReject = bookings.get(position);
                String rejectBookingId = toReject.getBookingID();


                // Construct the JSON data for the PUT request
                String jsonData = "[" +
                        "{" +
                        "\"bookingID\": " + toReject.getBookingID() + "," +
                        "\"userID\": " + toReject.getUserID() + "," +
                        "\"roomID\": " + toReject.getRoomID() + "," +
                        "\"checkInDate\": \"" + toReject.getCheckInDate() + "\"," +
                        "\"checkOutDate\": \"" + toReject.getCheckOutDate() + "\"," +
                        "\"bookingStatus\": \"Rejected\"" + // Set the status to Rejected
                        "}" +
                        "]";

                // Send the update to the backend server
                new HttpTask(jsonData, "PUT", new HttpTask.HttpDataResponse() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response
                        Toast.makeText(context, "Booking ID: " + rejectBookingId + " rejected", Toast.LENGTH_SHORT).show();
                        // Update the list and notify the adapter
                        bookings.remove(toReject);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        // Handle the failure
                        Toast.makeText(context, "Failed to reject booking", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }

        });
        return convertView;
    }

        @Override
        public void add (Booking booking){
            if ("Pending Confirm".equals(booking.getBookingStatus())) {
                super.add(booking);
            }
        }

        @Override
        public void addAll (List < Booking > bookings) {
            for (Booking booking : bookings) {
                if ("Pending Confirm".equals(booking.getBookingStatus())) {
                    super.add(booking); // Add only if status is 'Checked In'
                }
            }
        }
    }
