package com.levelupcluster.go4lunch.utils;

import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.data.GetRestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    private Restaurant getSingleNearbyPlace(JSONObject googlePlaceJSON) {

        Restaurant googleRestaurant = new Restaurant();

        try {
            googleRestaurant.setPhoto(150, googlePlaceJSON.getJSONArray("photos").getJSONObject(0).getString("photo_reference"));
        } catch (JSONException e) {
            googleRestaurant.setPhotoPlaceHolder();
            e.printStackTrace();
        }

        try {
            double longitude = Double.parseDouble(googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng"));
            double latitude = Double.parseDouble(googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat"));
            googleRestaurant.setName(googlePlaceJSON.getString("name"));
            googleRestaurant.setBusiness_status(googlePlaceJSON.getString("business_status"));
            googleRestaurant.setLatLng(new LatLng(latitude, longitude));
            googleRestaurant.setId(googlePlaceJSON.getString("place_id"));

            if (googlePlaceJSON.isNull("rating")) {
                googleRestaurant.setRating("null");
            } else {
                googleRestaurant.setRating(googlePlaceJSON.getString("rating"));
            }
            googleRestaurant.setVicinity(googlePlaceJSON.getString("vicinity"));


            if (googlePlaceJSON.isNull("opening_hours")) {
                googleRestaurant.setOpen_now(false);
            } else {
                googleRestaurant.setOpen_now(googlePlaceJSON.getJSONObject("opening_hours").getBoolean("open_now"));
            }
            //TODO add real CookStyle
            googleRestaurant.setCookStyle("test");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return googleRestaurant;
    }

    private RestaurantDetails getRestaurantDataDetails(JSONObject restaurantJson) {

        RestaurantDetails restaurantDtl = new RestaurantDetails();



        try {
            JSONObject result = restaurantJson.getJSONObject("result");
            String photoReference = result.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
            restaurantDtl.setPhoto(400, photoReference);
        } catch (JSONException e) {
            restaurantDtl.setPhotoPlaceHolder();
            e.printStackTrace();
        }


        try {
            JSONObject result = restaurantJson.getJSONObject("result");

            restaurantDtl.setName(result.getString("name"));

            if (!result.isNull("formatted_address")) {
                restaurantDtl.setVicinity(result.getString("formatted_address"));
            }

            if (!result.isNull("rating")) {
                restaurantDtl.setRating(result.getString("rating"));
            }

            if (!result.isNull("formatted_phone_number")) {
                restaurantDtl.setFormattedPhoneNumber(result.getString("formatted_phone_number"));
            }

            if (!result.isNull("website")) {
                restaurantDtl.setWebsite(result.getString("website"));
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return restaurantDtl;
    }


    private List<Restaurant> getAllNearbyPlaces(JSONArray jsonArray) {
        int counter = jsonArray.length();
        List<Restaurant> nearbyPlacesList = new ArrayList<>();
        Restaurant restaurant = null;

        for (int i = 0; i < counter; i++) {
            try {
                restaurant = getSingleNearbyPlace((JSONObject) jsonArray.get(i));
                nearbyPlacesList.add(restaurant);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return nearbyPlacesList;
    }

    public List<Restaurant> parseRestaurants(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return getAllNearbyPlaces(jsonArray);
    }

    public RestaurantDetails parseRestaurantDetails(String jsonData) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return getRestaurantDataDetails(jsonObject);
    }

}
