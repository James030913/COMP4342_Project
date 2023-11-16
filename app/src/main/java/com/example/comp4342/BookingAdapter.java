package com.example.comp4342;

import java.util.List;

public interface BookingAdapter {
    void clear();
    void addAll(List<Booking> bookings);
    void notifyDataSetChanged();
}

