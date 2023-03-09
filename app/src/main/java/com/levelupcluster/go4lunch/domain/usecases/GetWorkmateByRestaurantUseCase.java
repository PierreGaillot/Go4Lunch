package com.levelupcluster.go4lunch.domain.usecases;

import com.levelupcluster.go4lunch.data.usecases.GetWorkmateByRestaurantUseCaseImpl;
import com.levelupcluster.go4lunch.domain.models.Workmate;
import com.levelupcluster.go4lunch.utils.Callback;

import java.util.List;

public interface GetWorkmateByRestaurantUseCase {

    GetWorkmateByRestaurantUseCase instance = new GetWorkmateByRestaurantUseCaseImpl();

    void getWorkmateByRestaurant(String restaurantId, Callback<List<Workmate>> workmates);

}
