package com.example.comp4342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4342.adapter.RoomType;
import com.example.comp4342.adapter.RoomTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AfterActivity extends AppCompatActivity {

    List<RoomType> roomTypeList;
    RecyclerView roomTypeRecyclerView;
    RoomTypeAdapter adapter;

    int [] roomsPhoto = {R.drawable.room1, R.drawable.room2,R.drawable.room3};
    int HotelID;
    String userID;
    String start, end;
    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String URL = "https://great-grown-opossum.ngrok-free.app/roomTypes/byParam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        SharedPreferences sharedPref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = sharedPref.getString("UserId", null);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            start = bundle.getString("start");
            end = bundle.getString("end");

        }
        roomTypeList = new ArrayList<>();

        // Initialize RecyclerView
        roomTypeRecyclerView = findViewById(R.id.roomTypeRecyclerView);



        // Set Adapter


        HotelID = getIntent().getIntExtra("hotelID", -1);

        RoomTypeAdapter.OnItemClickListener listener = new RoomTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final RoomType item) {
                // Create an AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(AfterActivity.this);

                // Set the message
                builder.setMessage("Are you sure you want to select " + item.getRoomTypeName() + "?");

                // Add a positive button (yes button)
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// Create a new single thread executor
                        ExecutorService executor = Executors.newSingleThreadExecutor();

// Create a new handler on the main thread
                        Handler handler = new Handler(Looper.getMainLooper());

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // Network request goes here...

                                // Create a new OkHttpClient instance
                                OkHttpClient client = new OkHttpClient();

                                // Create JSON array
                                JSONArray jsonArray = new JSONArray();

                                // Generate a random roomID between 100 and 999
                                Random random = new Random();
                                int roomID = 100 + random.nextInt(300);  // Generates a random number between 100 (inclusive) and 999 (inclusive)

                                // Create JSON object for the new booking
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("userID", Integer.parseInt(userID));
                                    json.put("roomID", roomID); // Use the random roomID
                                    json.put("checkInDate", start);
                                    json.put("checkOutDate", end);
                                    json.put("bookingStatus", "Pending Confirm");

                                    // Add JSON object to JSON array
                                    jsonArray.put(json);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Create request body from JSON array
                                RequestBody body = RequestBody.create(jsonArray.toString(), MediaType.parse("application/json; charset=utf-8"));

                                // Create a new POST request
                                Request request = new Request.Builder()
                                        .url("https://great-grown-opossum.ngrok-free.app/bookings")
                                        .post(body)
                                        .build();

                                // Send the request asynchronously
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if (!response.isSuccessful()) {
                                            throw new IOException("Unexpected code " + response);
                                        } else {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);

                                                    // Start AfterActivity
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

                // Add a negative button (no button)
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the user's denial here, if necessary
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        adapter = new RoomTypeAdapter(roomTypeList, listener);

        // Set the adapter to the RecyclerView
        roomTypeRecyclerView.setAdapter(adapter);

        // Don't forget to set a LayoutManager on your RecyclerView
        roomTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            postRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void postRequest() throws IOException {

        // JSON format for the data to post
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String content = null;  // replace with your actual data
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hotelID", HotelID);
            content = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request body
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, content);

        // Building the request
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        // Making the request
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Parse JSON with JSONObject and JSONArray
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);

                        // Assuming roomTypeList and adapter are instance variables
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject roomTypeObject = jsonArray.getJSONObject(i);

                            int roomTypeID = roomTypeObject.getInt("roomTypeID");
                            int hotelID = roomTypeObject.getInt("hotelID");
                            String roomTypeName = roomTypeObject.getString("roomTypeName");
                            String description = roomTypeObject.getString("description");
                            int pricePerNight = roomTypeObject.getInt("pricePerNight");
                            int totalRooms = roomTypeObject.getInt("totalRooms");

                            RoomType roomType = new RoomType(roomTypeID, hotelID, roomTypeName, description, pricePerNight, totalRooms, roomsPhoto[i]); // Assuming you have an appropriate constructor and drawable resource
                            roomTypeList.add(roomType);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();  // Assuming adapter is your RecyclerView adapter
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}