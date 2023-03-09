package com.levelupcluster.go4lunch.data.usecases;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.data.repository.RestaurantRepository;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.domain.usecases.GetNearbyRestaurantsUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public class GetNearbyRestaurantsUseCaseImpl implements GetNearbyRestaurantsUseCase {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    @Override
    public void getNearbyRestaurants(LatLng userLastLatLng, Callback<List<Restaurant>> callback) {
        restaurantRepository.getNearbyRestaurants(callback, userLastLatLng);
    }
}
