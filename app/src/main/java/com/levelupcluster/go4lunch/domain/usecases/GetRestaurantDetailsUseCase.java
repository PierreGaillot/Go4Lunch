package com.levelupcluster.go4lunch.domain.usecases;

import com.levelupcluster.go4lunch.data.repository.RestaurantRepository;
import com.levelupcluster.go4lunch.data.usecases.GetNearbyRestaurantsUseCaseImpl;
import com.levelupcluster.go4lunch.data.usecases.GetRestaurantDetailsUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.utils.Callback;

public interface GetRestaurantDetailsUseCase {

    GetRestaurantDetailsUseCase instance = new GetRestaurantDetailsUseCaseImpl();

    public void getRestaurantDetails(String restaurantId, Callback<RestaurantDetails> callback);
}
