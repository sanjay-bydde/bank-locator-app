package com.example.SpringDemo.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.SpringDemo.Entity.Bank;

@Component
public class GoogleMapsAPI {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getCoordinatesFromZip(String zipcode) {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&key=" + apiKey;
            String response = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(response);
            if (!"OK".equals(json.getString("status"))) {
                throw new RuntimeException("Error from Google Maps API: " + json.getString("status"));
            }

            JSONObject location = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            return new double[]{lat, lng};

        } catch (Exception e) {
            System.out.println("Falling back to mock coordinates due to: " + e.getMessage());
            return new double[]{32.7767, -96.7970};
        }
    }

    public List<Bank> getNearbyBanksFromCoordinates(double lat, double lng) {
        try {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                    + "?location=" + lat + "," + lng
                    + "&radius=16093" 
                    + "&type=bank"
                    + "&key=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);

            if (!"OK".equals(json.getString("status"))) {
                throw new RuntimeException("Error from Places API: " + json.getString("status"));
            }

            JSONArray results = json.getJSONArray("results");
            List<Bank> banks = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String name = result.getString("name");
                String address = result.optString("vicinity", "No address available");

                JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                double bankLat = location.getDouble("lat");
                double bankLng = location.getDouble("lng");

                banks.add(new Bank(name, address, bankLat, bankLng));
            }

            return banks;

        } catch (Exception e) {
            System.out.println("Failed to fetch real-time banks: " + e.getMessage());
            return List.of();
        }
    }
}