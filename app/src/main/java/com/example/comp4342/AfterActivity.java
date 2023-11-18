package com.example.comp4342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Bundle;
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

public class AfterActivity extends AppCompatActivity {

    List<RoomType> roomTypeList;
    RecyclerView roomTypeRecyclerView;
    RoomTypeAdapter adapter;

    int [] roomsPhoto = {R.drawable.room1, R.drawable.room2,R.drawable.room3};
    int HotelID;
    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String URL = "https://great-grown-opossum.ngrok-free.app/roomTypes/byParam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

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
                        // Handle the user's affirmation here, for example:
                        // selectRoom(item);
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
        String content = "{\"hotelID\":1}";  // replace with your actual data

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