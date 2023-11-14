package com.example.comp4342;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchJsonTask extends AsyncTask<String, Void, List<Booking>> {
    private BookingEditAdapter adapter;

    // Constructor to accept the adapter
    public FetchJsonTask(BookingEditAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    protected List<Booking> doInBackground(String... urls) {
        String jsonStr = null;

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonStr = readStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parseJson(jsonStr);
    }


    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        return sb.toString();
    }

    private List<Booking> parseJson(String json) {
        List<Booking> bookings = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String bookingID = jsonObject.getString("bookingID");
                String userID = jsonObject.getString("userID");
                String roomID = jsonObject.getString("roomID");
                String checkInDate = jsonObject.getString("checkInDate");
                String checkOutDate = jsonObject.getString("checkOutDate");
                String bookingStatus = jsonObject.getString("bookingStatus");


                bookings.add(new Booking(bookingID, userID, roomID, checkInDate, checkOutDate, bookingStatus));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    @Override
    protected void onPostExecute(List<Booking> result) {
        // Update the ListView with the fetched data
        if (result != null) {
            adapter.clear();
            adapter.addAll(result);
            adapter.notifyDataSetChanged();
        }
    }


}
