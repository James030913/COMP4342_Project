package com.example.comp4342;

import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpGetHotel extends AsyncTask<Void, Void, String> {
    private String data;
    private String requestMethod;
    private HttpDataResponse callback;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public HttpGetHotel(String data, String requestMethod, HttpDataResponse callback) {
        this.data = data;
        this.requestMethod = requestMethod;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(data, JSON);
        Request request = new Request.Builder()
                .url("https://great-grown-opossum.ngrok-free.app/hotels")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // You may want to check the response code here
            if (!response.isSuccessful()) {
                // Handle the failure
                return null;
            }
            // Return the response body as string
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        // Check if the callback is null to avoid NullPointerException
        if (callback != null) {
            if (response != null && !response.isEmpty()) {
                callback.onResponse(response);
            } else {
                callback.onFailure();
            }
        }
    }

    public interface HttpDataResponse {
        void onResponse(String response);
        void onFailure();
    }
}