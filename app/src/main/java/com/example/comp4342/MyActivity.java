package com.example.comp4342;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        Button button = findViewById(R.id.my_button);
        button.setOnClickListener(view -> {
            URL url = buildUrlForApi();
            new UpdateBookingsTask().execute(url);
        });
    }

    private URL buildUrlForApi() {
        // Build and return the URL for updating bookings
        try {
            return new URL("https://great-grown-opossum.ngrok-free.app/login-user");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class UpdateBookingsTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection urlConnection = null;
            try {
                // Assuming you are sending a POST request
                urlConnection = (HttpURLConnection) urls[0].openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);



                // Example JSON data
                String jsonInputString = "[{ 'bookingID': 408, 'userID': 10, 'roomID': 5, 'checkInDate': '2023-01-01', 'checkOutDate': '2023-01-05', 'bookingStatus': 'confirmed' }]";

                try (OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream())) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Code to read the response
                // ...

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the result of the update operation
        }
    }
}
