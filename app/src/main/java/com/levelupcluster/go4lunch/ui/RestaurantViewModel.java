package com.levelupcluster.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetAllWorkmatesUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetNearbyRestaurantsUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetRestaurantDetailsUseCase;
import com.levelupcluster.go4lunch.domain.usecases.GetWorkmateByRestaurantUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewModel extends ViewModel {

    private final GetNearbyRestaurantsUseCase getNearbyRestaurantsUseCase = GetNearbyRestaurantsUseCase.instance;
    private final GetRestaurantDetailsUseCase getRestaurantDetailsUseCase = GetRestaurantDetailsUseCase.instance;

    private final GetWorkmateByRestaurantUseCase getWorkmateByRestaurantUseCase = GetWorkmateByRestaurantUseCase.instance;
    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Restaurant>> restaurants = _restaurants;

    public void updateMapLocation(LatLng userLastLatLng){
        getNearbyRestaurantsUseCase.getNearbyRestaurants(userLastLatLng, new Callback<List<Restaurant>>() {
            @Override
            public void onCallback(List<Restaurant> restaurantList) {
                _restaurants.postValue(restaurantList);
            }
        });
    }

    public void getRestaurantDetails(String restaurantId, Callback<RestaurantDetails> callback){
        getRestaurantDetailsUseCase.getRestaurantDetails(restaurantId, new Callback<RestaurantDetails>() {
            @Override
            public void onCallback(RestaurantDetails result) {
                callback.onCallback(result);
            }
        });
    }

    public void getWorkmateByRestaurant(String restaurantId, Callback<List<Workmate>> callback){
        getWorkmateByRestaurantUseCase.getWorkmateByRestaurant(restaurantId, new Callback<List<Workmate>>() {
            @Override
            public void onCallback(List<Workmate> result) {
                callback.onCallback(result);
            }
        });
    }
}
