package com.example.comp4342.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4342.R;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> bookings;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView bookingID;
        public TextView roomID;
        public TextView checkInDate;
        public TextView checkOutDate;
        public TextView bookingStatus;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            bookingID = view.findViewById(R.id.bookingID);
            roomID = view.findViewById(R.id.roomID);
            checkInDate = view.findViewById(R.id.checkInDate);
            checkOutDate = view.findViewById(R.id.checkOutDate);
            bookingStatus = view.findViewById(R.id.bookingStatus);
        }
    }

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bookingID.setText(String.valueOf("Booking ID: " + booking.getBookingID()));
        holder.roomID.setText(String.valueOf("Room ID: " + booking.getRoomID()));
        holder.checkInDate.setText("Check-in Date: " + booking.getCheckInDate());
        holder.checkOutDate.setText("Check-out Date: " + booking.getCheckOutDate());
        holder.bookingStatus.setText("Status: " + booking.getBookingStatus());
        holder.image.setImageResource(booking.getImage());  // Set the image resource

        // If you're loading images from a URL, you would use a library like Glide or Picasso here instead
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
}