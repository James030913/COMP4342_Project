package com.example.comp4342;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class FetchDataTask extends AsyncTask<URL, Void, String> {
    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtil.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String searchResults) {
        if (searchResults != null && !searchResults.equals("")) {
            // Update the UI with searchResults data.
        }
    }
}
