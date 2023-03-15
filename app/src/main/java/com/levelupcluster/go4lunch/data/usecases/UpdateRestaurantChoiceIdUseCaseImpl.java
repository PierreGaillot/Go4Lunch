package com.levelupcluster.go4lunch.data.usecases;

import com.levelupcluster.go4lunch.data.repository.UserRepository;
import com.levelupcluster.go4lunch.domain.usecases.UpdateRestaurantChoiceIdUseCase;
import com.levelupcluster.go4lunch.utils.Callback;

public class UpdateRestaurantChoiceIdUseCaseImpl implements UpdateRestaurantChoiceIdUseCase {
    private final UserRepository userRepository = UserRepository.getInstance();


    @Override
    public void updateRestaurantChoice(String restaurantId, Callback<Void> callback) {
        userRepository.updateRestaurantChoiceId(restaurantId, callback);
    }

}
