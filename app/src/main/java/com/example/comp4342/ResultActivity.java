package com.example.comp4342;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4342.adapter.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataClass> dataList;

    TextView text1, text2;
    DetailAdapter adapter;
    DataClass androidData;

    private int[] hotelPhoto = {
            R.drawable.hotel1,
            R.drawable.hotel2,
            R.drawable.hotel3,
            R.drawable.hotel4,
            R.drawable.hotel5,
            R.drawable.hotel6,
            R.drawable.hotel7,
            R.drawable.hotel8,
            R.drawable.hotel9,
            R.drawable.hotel10,
            R.drawable.hotel11,
            R.drawable.hotel12,
            R.drawable.hotel13,
            R.drawable.hotel14
    };
    String hotelName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        text1 = findViewById(R.id.Search1);
        text2 = findViewById(R.id.Date1);
        Intent intent = getIntent();
        String locations = intent.getStringExtra("Location");
        String start = intent.getStringExtra("Start");
        String end = intent.getStringExtra("End");

        text1.setText("You are searching: " + locations);
        text2.setText("From " + start + " To " + end);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ResultActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("location", locations);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonData = jsonObject.toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonData);

        Request request = new Request.Builder()
                .url("https://great-grown-opossum.ngrok-free.app/hotels/search")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle the error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Run on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseBody = response.body().string();
                                JSONArray jsonArray = new JSONArray(responseBody);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hotelObject = jsonArray.getJSONObject(i);
                                    String hotelName = hotelObject.getString("hotelName");
                                    String hotelID = hotelObject.getString("hotelID");
                                    String desc = hotelObject.getString("description");
                                    String loc = hotelObject.getString("location");
                                    // Create a new thread to fetch the room type data
                                    new Thread(() -> {
                                        try {
                                            // Create a JSON body with the hotelID
                                            String json = "{\"location\": 1}";

// Create a media type for JSON
                                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

// Create the request body
                                            RequestBody body = RequestBody.create(json, JSON);
                                            OkHttpClient client = new OkHttpClient();
                                            Request roomTypeRequest = new Request.Builder()
                                                    .url("https://great-grown-opossum.ngrok-free.app/roomTypes/byParam")
                                                    .post(body)
                                                    .build();

                                            Response roomTypeResponse = client.newCall(roomTypeRequest).execute();
                                            JSONArray roomTypeArray = new JSONArray(roomTypeResponse.body().string());
                                            int total = 0;
                                            for (int j = 0; j < roomTypeArray.length(); j++) {
                                                JSONObject roomTypeObject = roomTypeArray.getJSONObject(j);
                                                total += roomTypeObject.getInt("pricePerNight");
                                                System.out.println(total);
                                            }
                                            int averagePrice = total / roomTypeArray.length();

                                            // Add hotelName and averagePrice to the dataList
                                            DataClass androidData = new DataClass(hotelName, desc , "Avg:$" + averagePrice+ "/Night", hotelPhoto[Integer.parseInt(hotelID)-1], loc, Integer.valueOf(hotelID),start,end);
                                            dataList.add(androidData);

                                            // Update RecyclerView on the UI thread
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DetailAdapter adapter = new DetailAdapter(ResultActivity.this, dataList);
                                                    recyclerView.setAdapter(adapter);
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }).start();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        });
    }

}