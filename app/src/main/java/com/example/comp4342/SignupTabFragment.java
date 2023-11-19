package com.example.comp4342;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignupTabFragment extends Fragment {

    EditText username, email, phone, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        username = view.findViewById(R.id.signup_username);
        email = view.findViewById(R.id.signup_email);
        phone = view.findViewById(R.id.signup_phone);
        password = view.findViewById(R.id.signup_password);


        // Get reference to the button
        Button signupButton = view.findViewById(R.id.signup_button);

// Set the OnClickListener
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to create an account when the button is clicked
                createAccount();
            }
        });

    }

    private void createAccount() {

        // Get user input
        String usernameValue = username.getText().toString();
        String emailValue = email.getText().toString();
        String phoneValue = phone.getText().toString();
        String passwordValue = password.getText().toString();

// Create JSON array
        JSONArray jsonArray = new JSONArray();

// Create JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", usernameValue);
            jsonObject.put("password", passwordValue);
            jsonObject.put("email", emailValue);
            jsonObject.put("phoneNumber", phoneValue);
            jsonObject.put("name", usernameValue); // replace with actual name

            // Add JSON object to JSON array
            jsonArray.put(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Convert JSONArray to String
        String json = jsonArray.toString();

        // Create request body
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        // Create request
        Request request = new Request.Builder()
                .url("https://great-grown-opossum.ngrok-free.app/users")
                .post(requestBody)
                .build();

        // Create HTTP client
        OkHttpClient client = new OkHttpClient();

        // Send request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Switch to main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Create alert dialog
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Account Creation")
                                    .setMessage("Account successfully created!")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();
                        }
                    });
                }
            }
        });
    }
}