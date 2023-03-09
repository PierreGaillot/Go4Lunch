package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.RestaurantRepository;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.domain.usecases.GetRestaurantDetailsUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

public class GetRestaurantDetailsUseCaseImpl implements GetRestaurantDetailsUseCase {


    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    @Override
    public void getRestaurantDetails(String restaurantId, Callback<RestaurantDetails> callback) {
        restaurantRepository.getRestaurantDetails(restaurantId, callback);
    }
}
