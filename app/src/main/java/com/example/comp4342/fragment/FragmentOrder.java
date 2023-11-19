package com.example.comp4342.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp4342.R;
import com.example.comp4342.adapter.Booking;
import com.example.comp4342.adapter.BookingAdapter;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOrder#newInstance} factory method to
 * create an instance of this fragment.
 */


public class FragmentOrder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private BookingAdapter adapter;

    int userID;
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
    public FragmentOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOrder newInstance(String param1, String param2) {
        FragmentOrder fragment = new FragmentOrder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = Integer.parseInt(sharedPref.getString("UserId", null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sendPostRequest();

        return view;
    }

    private void sendPostRequest() {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        String url = "https://great-grown-opossum.ngrok-free.app/bookings/search";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    // Parse the data from the response
                    try {
                        JSONArray jsonArray = new JSONArray(responseBody);
                        List<Booking> bookingList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int bookingID = jsonObject.getInt("bookingID");
                            int roomID = jsonObject.getInt("roomID");
                            String checkInDate = jsonObject.getString("checkInDate");
                            String checkOutDate = jsonObject.getString("checkOutDate");
                            String bookingStatus = jsonObject.getString("bookingStatus");
                            bookingList.add(new Booking(bookingID, roomID, checkInDate, checkOutDate, bookingStatus, hotelPhoto[i]));
                        }

                        // Update the RecyclerView on the main thread
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new BookingAdapter(bookingList);
                                recyclerView.setAdapter(adapter);
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