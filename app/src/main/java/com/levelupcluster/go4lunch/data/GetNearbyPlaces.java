package com.levelupcluster.go4lunch.data;

import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.utils.Callback;
import com.levelupcluster.go4lunch.utils.DataParser;
import com.levelupcluster.go4lunch.utils.JsonTask;

import java.util.List;

public class GetNearbyPlaces  {

    private static final int PROXIMITY_RADIUS = 1000;
    private static final String API_KEY = "AIzaSyCEDe9eFsSku4dpBjkjVjqfXjUsL5lQixg";

    public void getRestaurants(LatLng position, Callback<List<Restaurant>> callback){
        String url = getUrl(position.latitude, position.longitude, "restaurant");
        downloadUrl(url, result -> {
            List<Restaurant> nearbyPlacesList;
            DataParser dataParser = new DataParser();
            nearbyPlacesList = dataParser.parseRestaurants(result);
            callback.onCallback(nearbyPlacesList);
        });
    }

    private String getUrl(double latitude, double longitude, String placeType) {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + latitude + "," + longitude +
                "&radius=" + PROXIMITY_RADIUS +
                "&type=" + placeType +
                "&key=" + API_KEY +
                "&sensor=true";
    }

    protected void downloadUrl(String url, Callback<String> callback) {
        new JsonTask(callback).execute(url);
    }
}
