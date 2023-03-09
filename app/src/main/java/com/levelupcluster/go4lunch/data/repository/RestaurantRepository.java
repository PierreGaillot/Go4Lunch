package com.levelupcluster.go4lunch.data.repository;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.data.GetRestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.User;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.utils.Callback;
import com.levelupcluster.go4lunch.data.GetNearbyPlaces;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    private static volatile RestaurantRepository instance;
    private WorkmateRepository workmateRepository = WorkmateRepository.getInstance();
    GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
    GetRestaurantDetails getRestaurantDetails = new GetRestaurantDetails();



    private RestaurantRepository() {
    }

    public static RestaurantRepository getInstance() {
        RestaurantRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (RestaurantRepository.class) {
            if (instance == null) {
                instance = new RestaurantRepository();
            }
            return instance;
        }
    }

    public void getNearbyRestaurants(Callback<List<Restaurant>> callback, LatLng userLastLatLng) {
        getNearbyPlaces.getRestaurants(userLastLatLng, callback);
    }

    public void getRestaurantDetails(String restaurantId, Callback<RestaurantDetails> callback) {
        getRestaurantDetails.getDetails(restaurantId, callback);
    }

    public void getWorkmates(String restaurantId, Callback<List<Workmate>> callback) {
        List<Workmate> wm = new ArrayList<>();
        workmateRepository.getWorkmates(new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> workmates) {
                for (Workmate workmate : workmates) {
                    if (workmate.getRestaurantChoiceId() != null) {
                        String workmateChoiceRestaurantId = workmate.getRestaurantChoiceId();
                        if (workmateChoiceRestaurantId.equals(restaurantId)) {
                            wm.add(workmate);
                        }
                    }
                }
                callback.onCallback(wm);
            }
        });
    }

}