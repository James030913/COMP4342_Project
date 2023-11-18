package com.example.comp4342;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;



public class LoginTabFragment extends Fragment {

    Button loginButton;
    EditText username, password;

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEyLCJpYXQiOjE3MDAzMjY2NzQsImV4cCI6MTcwMDMzMDI3NH0.oYU73TkymfBNnU6w15FYl-n6egPngR3kPQYH37fSSBI";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = view.findViewById(R.id.login_button);
        username = view.findViewById(R.id.login_user);
        password = view.findViewById(R.id.login_password);




        loginButton.setOnClickListener(v -> {
            // Create OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Create request body with MediaType JSON
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String jsonString = "{\"username\":\"" + username.getText().toString() +
                    "\",\"password\":\"" + password.getText().toString() + "\"}";
            RequestBody body = RequestBody.create(JSON, jsonString);

            // Create request
            Request request = new Request.Builder()
                    .url("https://great-grown-opossum.ngrok-free.app/login-user")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Handle the success response here
                        String jsonData = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            String tokenR = jsonObject.getString("token");

                            // Create request to get user id
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            String jsonString = "{\"username\":\"" + username.getText().toString() + "\"}"; // Assuming you pass the token in the request body
                            RequestBody body = RequestBody.create(JSON, jsonString);

                            Request requestUserId = new Request.Builder()
                                    .url("https://great-grown-opossum.ngrok-free.app/users/byParam")
                                    .post(body)
                                    .build();

                            client.newCall(requestUserId).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        String jsonData = response.body().string();
                                        try {
                                            JSONArray jsonArray = new JSONArray(jsonData);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0); // get the first object in the array
                                            String userId = jsonObject.getString("userID"); // get the userID from the object
                                            String userName = jsonObject.getString("username");
                                            // Save data in SharedPreferences
                                            SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("Username", userName);
                                            editor.putString("UserId", userId);
                                            editor.apply();
                                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                                            // Pass user id to HomeActivity
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        // Handle error response here
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Handle the error response here
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Login Failed")
                                        .setMessage("Wrong username or password")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        });
                    }
                }
            });
        });
    }
}