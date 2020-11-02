package com.example.vijaya.earthquakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        URLConnection urlConnection;
        try {
            url = new URL(requestUrl);
            urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) !=  null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            // Converting string builder to String and using it as JSON Response.
            jsonResponse = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = (JSONArray) jsonObject.get("features");
            for(int i =0;i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i).getJSONObject("properties");
                System.out.println(json);
                Earthquake earthquake = new Earthquake((double)json.get("mag"), (String) json.get("place"), (long)json.get("time"), (String) json.get("url"));
                earthquakes.add(earthquake);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
