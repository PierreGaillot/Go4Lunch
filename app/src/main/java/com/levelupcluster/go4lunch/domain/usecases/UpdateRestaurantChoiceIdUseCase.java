package com.levelupcluster.go4lunch.domain.usecases;

import com.levelupcluster.go4lunch.data.usecases.UpdateRestaurantChoiceIdUseCaseImpl;

public interface UpdateRestaurantChoiceIdUseCase {

    UpdateRestaurantChoiceIdUseCase instance = new UpdateRestaurantChoiceIdUseCaseImpl();

    void updateRestaurantChoice(String restaurantId);

}
