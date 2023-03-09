package com.levelupcluster.go4lunch.domain.usecases;

import com.google.android.gms.maps.model.LatLng;
import com.levelupcluster.go4lunch.data.usecases.GetNearbyRestaurantsUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.Restaurant;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public interface GetNearbyRestaurantsUseCase {

    GetNearbyRestaurantsUseCase instance = new GetNearbyRestaurantsUseCaseImpl();

    void getNearbyRestaurants(LatLng latLng, Callback<List<Restaurant>> callback);
}
