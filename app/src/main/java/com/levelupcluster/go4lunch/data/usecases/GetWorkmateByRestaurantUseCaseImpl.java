package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.RestaurantRepository;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.domain.usecases.GetWorkmateByRestaurantUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public class GetWorkmateByRestaurantUseCaseImpl implements GetWorkmateByRestaurantUseCase {

    RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();


    @Override
    public void getWorkmateByRestaurant(String restaurantId, Callback<List<Workmate>> callback) {
        restaurantRepository.getWorkmates(restaurantId, callback);
    }
}
